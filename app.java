class app {
	public static void main(String[] args) {
	
	}


	// group meetings that cannot happen at the same time, and can happen at the same time
	public List<List<Integer>> group(List<List<Integer>> meetings) {

		// find groups that can be grouped together, and groups that cannot
		HashMap<Integer, Set<Integer> coexist = new HashMap<>();
		HashMap<Integer, Set<Integer>> exclude = new HashMap<>();
		coexist.put(0, new HashSet<Integer>());
		exclude.put(0, new HashSet<Integer>());

		for (int meeting = 1; meeting < meetings.size(); meeting++) {
			for (int prev : coexist.keySet()) {
				if (collide(meetings, meeting, pre)) {
					
					// add to exclude
					exclude.get(prev).add(meeting);
					if (!exclude.containsKey(meeting)) {
						exclude.put(meeting, new HashSet<Integer>());
					}
					exclude.get(meeting).add(prev);
				} else {

					// add to coexis
					coexist.get(prev).add(meeting);
					if (!coexist.containsKey(meeting)) {
						coexist.put(meeting, new HashSet<Integer>());
					}
					coexist.get(meeting).add(prev);
				}
			}
		}


		// now we can perform a traversal
		// the idea is to choose this meeting or not choose this meeting
		HashMap<Integer, List<Integer>> evaluated = new HashSet<>();
		List<Integer> resultMeetings = traverse(0, meetings, coexist, exclude, evaluated);

		// now we have a list of meetings that can be grouped and reached heighest # of attendees
		List<List<Integer>> groups = new ArrayList<>();
		for (int m : resultMeetings) {
			groups.add(meetings.get(m));
		}
		return groups;
	}


	// the idea of recording the temporary results for a given meeting
	// res repressents the maximum of grouping results given the current meeting chosen/not chosen
	class Result {
		boolean chosen;
		List<Integer> res;
		Result(boolean chosen, List<Integer> res) {
			this.chosen = chosen;
			this.res = res;
		}
	}


	List<Integer> traverse(int meeting, 
			List<List<Integer>> meetings, 
			HashMap<Integer, Set<Integer> coexist, 
			HashMap<Integer, Set<Integer> exclude,
			HashMap<Integer, List<Integer>> evaluated) {

		if (evaluated.containsKey(meeting)) {
			return evaluated.get(meeting);
		}
		// pick this meeting, and travrse among meeting that can coexist
		List<Integer> choose = new ArrayList<>;
		int maxChoose = 0;
		int count = meetings.get(meeting).size();
		for (int canchoose : coexist.get(meeting)) {
			List<Ingteger> meetingList = traverse(canchoose, meetings, coexist, exclude);
			if (meetingList.size() > maxChoose) {
				maxChoose = meetingList.size();
				choose = meetingList;
			}
		}

		// do not pick current meeting
		List<Integer> skip = new ArrayList<>;
		int maxSkip = 0;
		for (int canchoose : exclude.get(meeting)) {
			List<Ingteger> meetingList = traverse(canchoose, meetings, coexist, exclude);
			if (meetingList.size() > maxSkip) {
				maxSkip = meetingList.size();
				skip = meetingList;
			}
		}

		if (maxSkip > maxChoose + count) {
			skip.add(meeting);
			evaluated.put(meeting, new Result(false, skip));
			return skip;
		} 
		evaluated.put(meeting, new Result(true, chosen));
		return chosen;
	}

}