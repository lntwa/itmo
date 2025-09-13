package org.example;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

public class App 
{
    public static void main( String[] args )
    {
        /*Deque<Integer> deque = new LinkedList<>();
        deque.add(3);
        deque.addFirst(4);
        deque.addFirst(3);
        deque.addLast(1);
        deque.removeFirst();
        deque.addLast(2);
        System.out.println(deque);*/

        /*Stream.of("libra", "scorpio", "sagittarius", "capricorn", "aquarius", "pisces").filter(s -> s.length() <= 8).skip(2).sorted().forEachOrdered(System.out::print);*/

        JMenuItem c = new JMenuItem("The Last Jedi");
        c.addItemListener(new {
            public void itemValueChanged(ItemEvent ev) {
                c.setBackground(Color.PINK);
            }
        });
    }
}
