package com.apocalypse.common.core.convert.impl;
import com.apocalypse.common.core.convert.TreeNodeConverter;
import com.apocalypse.common.core.module.TreeNode;
import com.apocalypse.common.core.vo.TreeNodeVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/8/27
 */
@Component
public class TreeNodeConverterImpl implements TreeNodeConverter {
    @Override
    public TreeNodeVO convert2TreeNodeVO(TreeNode treeNode) {
        if ( treeNode == null ) {
            return null;
        }

        TreeNodeVO treeNodeVO = new TreeNodeVO();

        treeNodeVO.setTitle( treeNode.getName() );
        treeNodeVO.setKey( treeNode.getId() );
        treeNodeVO.setOrderNum(treeNode.getOrderNum());
        List<TreeNode> list = treeNode.getChildren();
        if ( list != null ) {
            treeNodeVO.setChildren(convertChildren(list));
        }
        return treeNodeVO;
    }

    private List<TreeNodeVO> convertChildren(List<TreeNode> children) {
        if ( children == null ) {
            return null;
        }
        List<TreeNodeVO> treeNodeVOS = new ArrayList<>( children.size() );
        for ( TreeNode treeNode : children ) {
            treeNodeVOS.add( convert2TreeNodeVO( treeNode ) );
        }
        return treeNodeVOS;
    }
}
