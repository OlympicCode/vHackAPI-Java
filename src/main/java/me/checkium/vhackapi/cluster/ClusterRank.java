package me.checkium.vhackapi.cluster;

public enum ClusterRank {

    MEMBER("Member"),
    LEADER("Leader"),
    COLEADER("Co-Leader");

    String rank;

    ClusterRank(String rank) {
        this.rank = rank;
    }

    public String toString() {
        return rank;
    }

}
