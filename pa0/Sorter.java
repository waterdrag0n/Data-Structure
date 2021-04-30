/*
 * Name: Su Yong Chang
 * Student ID #: 2016125016
 */

/*
 * Do NOT import any additional packages/classes.
 * If you (un)intentionally use some additional packages/classes we did not
 * provide, you may receive a 0 for the homework.
 */

public class Sorter implements ISorter {
	public Sorter() { ; }

	@Override
	public int[] ascending(int[] a) {
		/*
		 * Input:
		 *  - an integer array A
		 *
		 * Output: a sorted array A in *ascending* order.
		 */
		int len = a.length;
		for(int i=0;i<len-1;i++){
			for(int j=1;j<len-i;j++){
				if(a[j-1]>a[j]){
					int tmp = a[j-1];
					a[j-1] = a[j];
					a[j] = tmp;
				}
			}
		}
		return a;
	}

	@Override
	public int[] descending(int[] a) {
		/*
		 * Input:
		 *  - an integer array A
		 *
		 * Output: a sorted array A in *descending* order.
		 */
		int len = a.length;
		for(int i=0;i<len-1;i++){
			for(int j=1;j<len-i;j++){
				if(a[j-1]<a[j]){
					int tmp = a[j-1];
					a[j-1] = a[j];
					a[j] = tmp;
				}
			}
		}
		return a;
	}
}
