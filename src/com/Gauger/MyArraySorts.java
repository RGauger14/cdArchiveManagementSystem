package com.Gauger;

import java.util.ArrayList;

public class MyArraySorts
{
    public static void bubbleSort(ArrayList<CDModel> cds)
    {
        int i;
        int j;
        CDModel temp;
        boolean swapped;
        for (i = 0; i < cds.size() - 1; i++)
        {
            swapped = false;
            for (j = 0; j < cds.size() - i - 1; j++)
            {
                if (cds.get(j).title.compareToIgnoreCase(cds.get(j + 1).title) > 0)
                {
                    temp = cds.get(j);
                    cds.set(j, cds.get(j + 1));
                    cds.set(j + 1, temp);
                    swapped = true;
                }
            }

            // IF no two elements were
            // swapped by inner loop, then break
            if (swapped == false)
                break;
        }
    }

    public static void insertionSort(ArrayList<CDModel> cds)
    {
        {
            int n = cds.size();
            for (int i = 1; i < n; ++i)
            {
                String key = cds.get(i).author;
                int j = i - 1;
                while (j >= 0 && cds.get(j).author.compareTo(key) > 0)
                {
                    cds.set(j + 1, cds.get(j));
                    j = j - 1;
                }
                cds.get(j + 1).author = key;
                ;
            }
        }
    }

    public static void selectionSort(ArrayList<CDModel> cds)
    {
        int n = cds.size();

        for (int i = 0; i < n - 1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < n; j++)
            {
                if (cds.get(j).barcode < cds.get(min_idx).barcode)
                {
                    min_idx = j;
                }
            }
            // Swap the found minimum element with the first
            // element
            CDModel temp = cds.get(min_idx);
            cds.set(min_idx, cds.get(i));
            cds.set(i, temp);
        }
    }
}

