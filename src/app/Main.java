package app;

import java.util.concurrent.TimeUnit;

import app.job.CouponExpirationDailyJob;

public class Main {

	public static void main(String[] args) {

		CouponExpirationDailyJob.start();

		for (int i = 0; i <= 10; i++) {
			try {
				TimeUnit.SECONDS.sleep(3);
				System.out.println(i);
			} catch (Exception ex) {
				System.out.println(ex);
			}

		}

		CouponExpirationDailyJob.stop();
	}

}
