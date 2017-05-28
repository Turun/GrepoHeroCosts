public class Main
{
    final int len;
    int[][] data;
    /**
     *  deltaCost, LevelFrom, Costs, LevelTo,
     *  deltaCost, LevelFrom, Costs, LevelTo,
     *  deltaCost, LevelFrom, Costs, LevelTo,
     *  deltaCost, LevelFrom, Costs, LevelTo,
     *  deltaCost, LevelFrom, Costs, LevelTo,
     *  ...
     */
    
    /**
     * the Main class
     */
    public Main(int length){
        len = length;
        data = new int[len][4];
        
        setUp();
        calculateCosts();
    }
    
    /**
     * sets the Array values for LevelFrom, LevelTo and the deltaCosts
     */
    private void setUp(){
        for(int i = 1; i<len; i++){
            int deltaCosts = ((int)(i/3))*2+1;
            data[i][1] = i;
            data[i][3] = i+1;
            data[i][0] = deltaCosts;
        }
    }
    
    /**
     * calculates the costs for each level. the costs for each level is the cost from the previous level + deltaCosts
     */
    private void calculateCosts(){
        data[1][2] = 12;
        for(int i = 2; i<len; i++){
            data[i][2] = data[i-1][2] + data[i][0];
        }
    }
    
    /**
     * returns the sum of the Costs from level "from" to level "to"
     * 
     *      @param from: the start level
     *      @param to: the end  level
     */
    public int sum(int from, int to){
        int re = 0;
        for(int i = to; i>from; i--){
            re += data[i][2];
        }
        return re;
    }
}
