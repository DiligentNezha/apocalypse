package com.apocalypse.common.core.convert;


import com.apocalypse.common.core.module.TreeNode;
import com.apocalypse.common.core.vo.TreeNodeVO;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/7/6
 */
public interface TreeNodeConverter {
    TreeNodeVO convert2TreeNodeVO(TreeNode treeNode);
}
