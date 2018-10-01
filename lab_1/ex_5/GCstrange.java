import java.util.*;

class GCstrange {
	static int dump = 0;
	static final int COUNT = 10000000;

	public static void main(String[] args) throws InterruptedException {
		GCstrange tmp = null;
		for (int i = 1; i < COUNT; i++) {
			if (i % (COUNT / 100) == 0) {
				System.out.println(i + " => " + dump);
				Thread.sleep(1000);
			}
			tmp = new GCstrange();

		}
	}

	public GCstrange() {
	}

	protected void finalize() {
		dump += 1;
	}
}
