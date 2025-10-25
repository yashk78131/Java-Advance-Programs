class CapacityToShipPackages{

    static boolean canCarry(int[] weights, int days, int cap){
        int day = 1, load=0;
        for(int w:weights){
            if(load + w>cap){
                day++;
                load=0;
            }
            load+=w;
        }
        return day<=days;
    }
    public int shipWithinDays(int[] weights, int days) {
        int low=0,high=0;
        for(int w : weights){
            low = Math.max(low,w);
            high+= w;
        }
        while(low<high){
            int mid = low +(high-low)/2;
            if(canCarry(weights,days,mid)){
                high = mid;
            }
            else{
                low = mid+1;
            }
        }
        return low;
    }
}
