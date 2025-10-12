import java.util.*;
public class Solution {
	public static int getMaxWidth(TreeNode root) {
        if(root==null)
            return 0;
        int m=0,w=0;
        Queue<TreeNode> q=new LinkedList<>();
        q.offer(root);
        q.offer(null);
        while(!q.isEmpty()){
           TreeNode r=q.poll();
            if(r==null){
                if(!q.isEmpty()){
                    q.offer(null);
                }
                w=q.size()-1;
                continue;
            }
            m=Math.max(m,w);
            if(r.left!=null)
                q.offer(r.left);
            if(r.right!=null)
                q.offer(r.right);
        }
        return m;
	}
}
