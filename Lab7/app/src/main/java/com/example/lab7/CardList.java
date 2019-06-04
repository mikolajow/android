package com.example.lab7;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class CardList {
    static List<Card> lista;
    static int liczba = 3;
    public static List<Card> buildCardList(Context ctx,
                                           int count)
    {
        String title[] = {
                "Tytul 1",
                "Tytul 2",
                "Tytul 3" };
        String desc[] = {
                "Opis tutulu 1",
                "Opis tutulu 2",
                "Opis tutulu 3" };
        int[] ids = new int[3];
        ids[0] = R.drawable.starmie;
        ids[1] = R.drawable.shellder;
        ids[2] = R.drawable.gastly;
        if (count>liczba)
            return null;
        lista = new ArrayList<>();
        for (int idx = 0; idx < liczba; ++idx) {
            Card card = new Card();
            card.title = title[idx];
            card.description = desc[idx];
            card.image = ctx.getResources().getDrawable(ids[idx]);
            lista.add(card);
        }
        return lista;
    }
}