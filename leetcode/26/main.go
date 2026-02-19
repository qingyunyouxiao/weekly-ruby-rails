package main

import "fmt"

func main() {
	// 测试 1 - 包含重复元素
	nums1 := []int{1, 1, 2}
	fmt.Println(nums1)
	fmt.Printf("Length of unique elements: %d, Unique elements: %v\n",
		removeDuplicates(nums1), nums1[:removeDuplicates(nums1)])

	// 测试 2 - 包含多个重复元素
	nums2 := []int{0, 0, 1, 1, 1, 2, 2, 3, 3, 4}
	fmt.Println(nums2)
	fmt.Printf("Length of unique elements: %d, Unique elements: %v\n",
		removeDuplicates(nums2), nums2[:removeDuplicates(nums2)])

	// 测试 3 - 空数组
	nums3 := []int{}
	fmt.Println(nums3)
	fmt.Printf("Length of unique elements: %d, Unique elements: %v\n",
		removeDuplicates([]int{}), nums3[:removeDuplicates(nums3)])
}

func removeDuplicates(nums []int) int {
	if len(nums) == 0 {
		return 0
	}
	slow := 0
	for fast := 1; fast < len(nums); fast++ {
		if nums[fast] != nums[slow] {
			slow++
			nums[slow] = nums[fast]
		}
	}
	return slow + 1
}
