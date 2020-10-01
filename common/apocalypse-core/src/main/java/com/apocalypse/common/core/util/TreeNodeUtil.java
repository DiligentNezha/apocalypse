package com.apocalypse.common.core.util;

import cn.hutool.core.collection.CollUtil;
import com.apocalypse.common.core.convert.TreeNodeConverter;
import com.apocalypse.common.core.module.TreeNode;
import com.apocalypse.common.core.vo.TreeNodeVO;
import com.apocalypse.common.data.mybatis.util.bean.BeanFactoryUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/7/9
 */
public class TreeNodeUtil {

    public static <T extends TreeNode<T>> void toTree(List<T> treeNodeList) {
        List<T> topTreeNode = treeNodeList.stream().filter(TreeNode::isTopNode).collect(Collectors.toList());
        List<T> notTopOpsElements = treeNodeList.stream().filter(treeNode -> !treeNode.isTopNode()).collect(Collectors.toList());

        topTreeNode.forEach(parent -> {
            toTree(parent, notTopOpsElements);
        });
        treeNodeList.clear();
        treeNodeList.addAll(topTreeNode);
        sort(treeNodeList);
    }

    public static <T extends TreeNode<T>> List<TreeNodeVO> toTreeNodes(List<T> treeNodeList) {
        TreeNodeConverter treeNodeConverter = BeanFactoryUtil.getBean(TreeNodeConverter.class);
        sort(treeNodeList);
        return treeNodeList.stream()
                .map(treeNode -> treeNodeConverter.convert2TreeNodeVO(treeNode))
                .collect(Collectors.toList());
    }

    private static <T extends TreeNode<T>> void toTree(T parent, List<T> treeNodeList) {
        List<T> childrens = treeNodeList.stream().filter(treeNode -> parent.getId().equals(treeNode.getParentId())).collect(Collectors.toList());
        List<T> notChildrens = treeNodeList.stream().filter(treeNode -> !parent.getId().equals(treeNode.getParentId())).collect(Collectors.toList());

        if (CollUtil.isNotEmpty(childrens)) {
            childrens.stream().forEach(children -> {
                List<T> grandchildrens = notChildrens.stream().filter(grandchildren -> children.getId().equals(grandchildren.getParentId())).collect(Collectors.toList());
                if (CollUtil.isNotEmpty(grandchildrens)) {
                    children.setChildren(grandchildrens);
                }
            });
            parent.setChildren(childrens);
        }
    }

    private static <T extends TreeNode<T>> void sort(List<T> treeNodeList) {
        Comparator<TreeNode<T>> comparator = Comparator.comparingInt(TreeNode::getOrderNum);
        if (CollUtil.isNotEmpty(treeNodeList)) {
            treeNodeList.forEach(t -> {
                if (CollUtil.isNotEmpty(t.getChildren())) {
                    sort(t.getChildren());
                }
            });
            Collections.sort(treeNodeList, comparator);
        }
    }

}
