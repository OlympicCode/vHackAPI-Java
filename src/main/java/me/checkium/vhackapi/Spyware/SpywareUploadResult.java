package me.checkium.vhackapi.Spyware;

public class SpywareUploadResult {

    protected boolean success;
    protected SpywareUploadResultEnum result;

    public SpywareUploadResult(String resultString) {
        switch (resultString) {
            case "0": {
                result = SpywareUploadResultEnum.SUCCESS;
                success = true;
                break;
            }
            case "7": {
                result = SpywareUploadResultEnum.IP_DOES_NOT_EXIST;
                break;
            }
            case "11": {
                result = SpywareUploadResultEnum.SPYWARE_ALREADY_UPLOADED;
                break;
            }
            case "14": {
                result = SpywareUploadResultEnum.ALL_SPYWARE_SLOTS_FULL;
                break;
            }
            default:{
                result = null;
                break;
            }
        }
    }

    public SpywareUploadResultEnum getResult() {
        return result;
    }

    public boolean wasSuccessfull() {
        return success;
    }

}
