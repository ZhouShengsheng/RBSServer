package ncu.zss.rbs.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;

import ncu.zss.rbs.model.RoomBooking;

public class TimeIntervalUtil {
	
	static Logger logger = Logger.getLogger(TimeIntervalUtil.class);

	/**
	 * Get time interval list from json string.
	 * 
	 * @param timeIntervals
	 * @return
	 */
	public static ArrayList<ArrayList<Date>> getTimeIntervalListFromJsonString(String timeIntervals) {
		ArrayList<ArrayList<Date>> timeIntervalList = new ArrayList<>();
		JSONArray times = JSONArray.parseArray(timeIntervals);
		if (times == null) {
			return null;
		}
		for (Object object : times) {
			JSONArray timeIntervalObject = (JSONArray)object;
			ArrayList<Date> timeInterval = new ArrayList<>();
			timeInterval.add(timeIntervalObject.getDate(0));
			timeInterval.add(timeIntervalObject.getDate(1));
			timeIntervalList.add(timeInterval);
		}
		return timeIntervalList;
	}
	
	/**
	 * Convert time interval list to timestamp interval list.
	 * 
	 * @param timeIntervalList
	 * @return
	 */
	public static ArrayList<ArrayList<Long>> convertTimeIntervalListToTimestampIntervalList(ArrayList<ArrayList<Date>> timeIntervalList) {
		ArrayList<ArrayList<Long>> timestampIntervalList = new ArrayList<>();
		for (ArrayList<Date> timeInterval : timeIntervalList) {
			ArrayList<Long> timestampInterval = new ArrayList<>();
			timestampInterval.add(timeInterval.get(0).getTime());
			timestampInterval.add(timeInterval.get(1).getTime());
			timestampIntervalList.add(timestampInterval);
		}
		return timestampIntervalList;
	}
	
	/**
	 * Check time interval overlaps.
	 * 
	 * @param roomBookingList
	 * @param timeIntervalList
	 * @param willGetOverlappedTimeIntervalList
	 * @param overlappedTimeIntervalList
	 * @return Is overlapped.
	 */
	public static boolean checkTimeIntervalOverlaps(List<RoomBooking> roomBookingList, ArrayList<ArrayList<Date>> timeIntervalList, 
			boolean willGetOverlappedTimeIntervalList, ArrayList<ArrayList<Date>> overlappedTimeIntervalList) {
		// Convert to timestamps.
		ArrayList<ArrayList<Long>> timestampIntervalList = TimeIntervalUtil.convertTimeIntervalListToTimestampIntervalList(timeIntervalList);
		
		for (RoomBooking roomBooking : roomBookingList) {
			roomBooking.setFromTimestamp(roomBooking.getFromtime().getTime());
			roomBooking.setToTimestamp(roomBooking.getTotime().getTime());
			logger.info(String.format("%s%s: %s - %s", roomBooking.getRoombuilding(), roomBooking.getRoomnumber(),
					roomBooking.getFromtime(), roomBooking.getTotime()));
		}
		boolean timeIntervalOverlapped = false;
		int timeIntervalCount = timestampIntervalList.size();
		for (int i = 0; i < timeIntervalCount; i++) {
			ArrayList<Long> timestampInterval = timestampIntervalList.get(i);
			for (RoomBooking roomBooking : roomBookingList) {
				long fromTimestamp = timestampInterval.get(0);
				if (fromTimestamp >= roomBooking.getToTimestamp()) {
					continue;
				}
				
				long toTimestamp = timestampInterval.get(1);
				if (toTimestamp <= roomBooking.getFromTimestamp()) {
					continue;
				}
				
				timeIntervalOverlapped = true;
				if (willGetOverlappedTimeIntervalList) {
					overlappedTimeIntervalList.add(timeIntervalList.get(i));
				} else {
					logger.info("Time interval overlapped!");
					return timeIntervalOverlapped;
				}
			}
		}
		
		return timeIntervalOverlapped;
	}
	
}
