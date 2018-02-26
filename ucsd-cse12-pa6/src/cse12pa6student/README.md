1. Filter Process;
	I check the initial character of each String. If it's neither starting with a letter nor a digit, the String would be considered NOT a word. For example "!!" is not counted Otherwise it counts as a word. For example "CSE12" and "2018Season" are counted as words. Also I check "apostrophes". If a single String starts with an apostrophes, it would be attached to the end of last String to ensure apostrophes work properly. For example "he","'s" would become "he's"

2. Database runtime analysis
	The running time is around 22 seconds. And my generateDatabase method should be a O(n^2*log(n)), where n are respectively the number of files (in path), the total number of words from data, and log(n) refers to the runtime of set method in BSTMap. The size of input is indicated by the number of words from the data (equally the size of allWord list).

3. Graph-making runtime analysis
	Making graph for 1-gram queries took 319 milliseconds. 2-gram took 309 milliseconds and 3-gram took 479 milliseconds. It seems like the "n" doesn't affect too much. The big-O bound should be O(n^2*log(n)^2), where the first n is the number of queries, and the second n is the number of years, and log(n)^2 refers to the runtime of calling get method of two nested DefaultMap.