package mcore.about_sort;

public class MyShort {

	public static void main(String[] args){
		ShortNums sn=new ShortNums();
		int[] nums = {3,6,1,5,9,4,2,8,7,0};
		sn.bubble(nums);
		//sn.chose(nums);
		//sn.quickChose(nums);
		display(nums);
	}
	
	public static void display(int[] n){
		for(int i : n){
			System.out.print(i+" ");
		}
		System.out.println(" ");
	}
}

class ShortNums{
	
	public void bubble(int[] arr){
		for(int i=0;i<arr.length-1;i++){
			for(int j=0;j<arr.length-1-i;j++){
				//一遍一遍的往后推
				if(arr[j]>arr[j+1]){
					int temp=arr[j];
					arr[j]=arr[j+1];
					arr[j+1]=temp;
				}
			}
		}
	}
	
	public void chose(int[] arr){
		for(int i=0;i<arr.length;i++){
			for(int j=i+1;j<arr.length;j++){
				if(arr[i]>arr[j]){
					int temp=arr[i];
					arr[i]=arr[j];
					arr[j]=temp;
				}
			}
		}
	}
	//选出最大的记下index后交换
	public void quickChose(int[] arr){
		for(int i=0;i<arr.length;i++){
			int min=i;
			for(int j=i+1;j<arr.length;j++){
				if(arr[min]>arr[j]){
					min=j;
				}
			}
			if(min!=i){
				int temp=arr[i];
				arr[i]=arr[min];
				arr[min]=temp;
			}
			
		}
	}
	
	
	
}