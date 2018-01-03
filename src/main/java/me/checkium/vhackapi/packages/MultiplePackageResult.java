package me.checkium.vhackapi.packages;

import java.util.HashMap;

public class MultiplePackageResult {

    private int money;
    private int netcoins;
    private int av;
    private int fw;
    private int ipsp;
    private int pcs;
    private int sdk;
    private int spam;
    private int scan;
    private int adw;
    private int boost;
    private HashMap<PackageResults, Integer> results = new HashMap<>();

    public MultiplePackageResult(PackageResult[] res){
        for (PackageResult r : res){
            PackageResults result = r.getResultType();
            int n = r.getResultAmount();
            switch(result) {
                case MONEY: {
                    money += n;
                }
                case NETCOINS: {
                    netcoins += n;
                }
                case AV: {
                    av += n;
                }
                case FW: {
                    fw += n;
                }
                case IPSP: {
                    ipsp += n;
                }
                case BTNTPC: {
                    pcs += n;
                }
                case SDK: {
                    sdk += n;
                }
                case SPAM: {
                    spam += n;
                }
                case SCAN: {
                    scan += n;
                }
                case ADW: {
                    adw += n;
                }
                case BOOST: {
                    boost += n;
                }
            }
        }
    }

    public HashMap<PackageResults, Integer> getResults(){
        for (PackageResults result : PackageResults.values()){
            switch(result){
                case MONEY:{
                    results.put(result, money);
                }
                case NETCOINS: {
                    results.put(result, netcoins);
                }
                case AV:{
                    results.put(result, av);
                }
                case FW:{
                    results.put(result, fw);
                }
                case IPSP:{
                    results.put(result, ipsp);
                }
                case BTNTPC:{
                    results.put(result, pcs);
                }
                case SDK:{
                    results.put(result, sdk);
                }
                case SPAM:{
                    results.put(result, spam);
                }
                case SCAN:{
                    results.put(result, scan);
                }
                case ADW:{
                    results.put(result, adw);
                }
                case BOOST:{
                    results.put(result, boost);
                }
            }
        }

        return results;
    }

}
