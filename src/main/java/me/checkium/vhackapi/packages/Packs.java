package me.checkium.vhackapi.packages;

public enum Packs {

    BONUS1(1, 200),
    BONUS10(2, 1800),
    BONUS100(3, 17000),
    BONUS500(4, 82000);

    private int id, cost;

    Packs(int id, int cost) {

        this.id = id;
        this.cost = cost;

    }

    public int getPackID() {

        return id;

    }

    public int getPackCost() {

        return cost;

    }

}
