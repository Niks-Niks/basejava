package main;

import main.model.Resume;
import main.storage.*;

/**
 * Test for your ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final AbstractStorage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume resume1 = new Resume("fullName");
        resume1.setUuid("uuid1");
        Resume resume2 = new Resume("fullName");
        resume2.setUuid("uuid2");
        Resume resume3 = new Resume("fullName");
        resume3.setUuid("uuid3");

        ARRAY_STORAGE.save(resume1);
        ARRAY_STORAGE.save(resume2);
        ARRAY_STORAGE.save(resume3);

        System.out.println("Size: " + ARRAY_STORAGE.size());
        System.out.println("Get r1: " + ARRAY_STORAGE.get(resume1.getUuid()));

//        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        ARRAY_STORAGE.update(resume1);
        System.out.println("Get r1 update: " + ARRAY_STORAGE.get(resume1.getUuid()));
        printAll();
        ARRAY_STORAGE.delete(resume2.getUuid());

        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
