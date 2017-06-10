package com.monchickey.collection;

import java.util.Comparator;

public class UserComparator implements Comparator<User> {

    @Override
    public int compare(User oo1, User oo2) {
        return oo1.getUsername().compareTo(oo2.getUsername());
    }

}
