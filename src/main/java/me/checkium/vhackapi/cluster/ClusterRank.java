package me.checkium.vhackapi.cluster;

public enum ClusterRank {

    MEMBER   ("Member"),
    LEADER   ("Leader"),
	COLEADER ("Co-Leader");
	
	String tostring;
	
	ClusterRank(String tostring){
		
		this.tostring = tostring;
		
	}
	
	public String toString(){
		
		return tostring;
		
	}
	
}
