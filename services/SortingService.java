package services;

import patient.Patient;

public class SortingService {


    public void displayPatientsTable(Patient[] arr, String title) {
        System.out.println("\n" + title);
        System.out.println("+-----+----------------------+-----+-------------------------+");
        System.out.println("| No. | Name                 | Age | Email                   |");
        System.out.println("+-----+----------------------+-----+-------------------------+");
        int count = 1;
        for (Patient p : arr) {
            if (p != null) {
                System.out.printf("| %-3d | %-20s | %-3d | %-23s |\n",
                    count++, p.getName(), p.getAge(), p.getEmail());
            }
        }
        System.out.println("+-----+----------------------+-----+-------------------------+");
    }

    // Bubble Sort by Age
    public void bubbleSortByAge(Patient[] arr) {
        displayPatientsTable(arr, "Before Bubble Sort");

        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] != null && arr[j + 1] != null &&
                    arr[j].getAge() > arr[j + 1].getAge()) {

                    Patient temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        displayPatientsTable(arr, "After Bubble Sort (Sorted by Age)");
    }


    // Selection Sort by Age
    public void selectionSortByAge(Patient[] arr) {
        displayPatientsTable(arr, "Before Selection Sort");

        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] != null && arr[minIndex] != null &&
                    arr[j].getAge() < arr[minIndex].getAge()) {
                    minIndex = j;
                }
            }
            Patient temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }

        displayPatientsTable(arr, "After Selection Sort (Sorted by Age)");
    }
}
