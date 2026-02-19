package main

import (
	"reflect"
	"testing"
)

func TestRemoveDuplicates(t *testing.T) {
	tests := []struct {
		name     string // 测试用例名称
		input    []int  // 输入数组
		expected []int  // 删除重复项后的预期数组（前 k 个元素）
		k        int    // 唯一元素的预期长度
	}{
		{
			name:     "basic test",
			input:    []int{1, 1, 2},
			expected: []int{1, 2},
			k:        2,
		},
		{
			name:     "multiple duplicates",
			input:    []int{0, 0, 1, 1, 1, 2, 2, 3, 3, 4},
			expected: []int{0, 1, 2, 3, 4},
			k:        5,
		},
	}

	// 遍历所有测试用例
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			// 创建输入的副本以避免修改原始测试数据
			nums := make([]int, len(tt.input))
			copy(nums, tt.input)

			// 执行被测试的函数
			k := removeDuplicates(nums)

			// 验证返回的长度 k 是否正确
			if k != tt.k {
				t.Errorf("expected length %d, got %d", tt.k, k)
			}

			// 验证数组的前 k 个元素是否为预期的唯一值
			if !reflect.DeepEqual(nums[:k], tt.expected) {
				t.Errorf("expected array %v, got %v", tt.expected, nums[:k])
			}
		})
	}
}
