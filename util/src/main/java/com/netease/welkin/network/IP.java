package com.netease.welkin.network;

public class IP {
	public String host;
	public int port;

	public IP(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public String toString() {
		return "host=" + host + ", port=" + port;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + port;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		IP other = (IP) obj;
		if (host != null && other.host != null && host.equals(other.host) && port == other.port) {
			return true;
		}
		return false;
	}

}
