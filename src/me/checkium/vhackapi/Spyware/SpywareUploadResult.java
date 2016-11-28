package me.checkium.vhackapi.Spyware;

public class SpywareUploadResult {

	protected boolean success;
	protected SpywareUploadResultEnum result;
	
	public SpywareUploadResult(String resultString)
	{
		switch (resultString){
			case "0":
				result = SpywareUploadResultEnum.success;
				success = true;
				break;
			case "7":
				result = SpywareUploadResultEnum.ip_does_not_exists;
				break;
			case "11":
				result = SpywareUploadResultEnum.spyware_already_uploaded;
				break;
			case "14":
				result = SpywareUploadResultEnum.all_spyware_slots_full;
				break;
		}
	}
	
	public SpywareUploadResultEnum getResult()
	{
		return result;
	}
	
	public boolean wasSuccessfull()
	{
		return success;
	}
	
}
