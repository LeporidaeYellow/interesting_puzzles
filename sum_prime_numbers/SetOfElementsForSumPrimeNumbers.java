package sumprimenumbers;

import java.util.*;
import java.util.stream.IntStream;

public class SetOfElementsForSumPrimeNumbers {

    private static int range;
    // Data Structure:
    // TableCell => TableRow => SummaryTable
    private static SummaryTable summaryTable = new SummaryTable();

    public static void calculate(int rangeValue) {
        range = rangeValue;
        storeResult(Prime.getSumPrimeList());
        // show info
        System.out.println(summaryTable.keySet());
        // show info
        for (Integer key: summaryTable.keySet()) {
            System.out.println(key + " = " + summaryTable.getTableRow(key));
        }
    }

    private static void dropOneElement(List<Integer> numbersList) {
        for (int i = 0; i < numbersList.size(); i++) {
            List<Integer> integerList = new ArrayList<>(numbersList);
            integerList.remove(i);
            storeResult(integerList);
        }
    }

    private static void storeResult(List<Integer> numbersList) {
        Integer sum = numbersList.stream().reduce(0, Integer::sum);
        if (sum > 0 && sum <= range) {
            summaryTable.addTableCell(numbersList, sum);
        }
        dropOneElement(numbersList);
    }


    // Summary Table
    private static class SummaryTable {
        private Hashtable<Integer, TableRow> hashTable;

        public SummaryTable() {
            this.hashTable = new Hashtable<>();
        }

        private Set<Integer> keySet() {
            return this.hashTable.keySet();
        }

        public void addTableCell(List<Integer> numbersList, int sum) {
            TableCell tableCell = new TableCell(numbersList);
            if (!this.hashTable.containsKey(sum)) {
                this.hashTable.put(sum, new TableRow(tableCell));
            }
            if (!this.hashTable.get(sum).getTableRow().contains(tableCell)) {
                this.hashTable.get(sum).addTableCell(tableCell);
            }
        }

        public TableRow getTableRow(Integer key) {
            return hashTable.get(key);
        }

        @Override
        public String toString() {
            return hashTable.toString();
        }
    }


    // TableRow
    private static class TableRow {
        private List<TableCell> tableRow;

        public TableRow() {
            this.tableRow = new ArrayList<>();
        }

        public TableRow(TableCell tableCell) {
            this.tableRow = new ArrayList<>();
            tableRow.add(tableCell);
        }

        public List<TableCell> getTableRow() {
            return tableRow;
        }

        public void addTableCell(TableCell tableCell) {
            this.tableRow.add(tableCell);
        }

        @Override
        public String toString() {
            return tableRow.toString();
        }
    }

    // TableCell
    private static class TableCell {
        private List<Integer> tableCell;

        public TableCell() {
            this.tableCell = new ArrayList<>();
        }

        public TableCell(List<Integer> tableCell) {
            this.tableCell = tableCell;
        }

        @Override
        public String toString() {
            return tableCell.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TableCell tmp = (TableCell) o;
            return Objects.equals(tableCell, tmp.tableCell);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tableCell);
        }
    }

    private static class Prime {
        private static int sum = 0;

        public static List<Integer> getSumPrimeList() {
            List<Integer> list = new ArrayList<>();
            list = fillListWithPrimeNumbers(list);
            int correction = range - sum;
            if (correction > 0) list.add(correction);
            return list;
        }

        private static List<Integer> fillListWithPrimeNumbers(List<Integer> list) {
            List<Integer> primeList = getPrimeList();
            for (int i = 0; sum < range - 2 ; i++) {
                list.add(primeList.get(i));
                sum += primeList.get(i);
            }
            return list;
        }

        private static List<Integer> getPrimeList() {
            List<Integer> list = new ArrayList<>();
            for (int i = 1; i <= range; i++) {
                if (!isPrime(i)) list.add(i);
            }
            return list;
        }

        private static boolean isPrime(final int number) {
            return IntStream.rangeClosed(2, number / 2).anyMatch(i -> number % i == 0);
        }
    }
}
