package com.company;

import java.util.Arrays;

public class SimpleDivideCompute implements ComputeRunnable {

	SimpleShareData ssd;
	int size;
	int offset;
	public SimpleDivideCompute(SimpleShareData ssd, int size, int offset) {
		this.ssd = ssd;
		this.size = size;
		this.offset = offset;
	}

	@Override
	public void run() {
		go();

	}

	@Override
	public void go() {
		System.out.println("开始计算随机数: " + size + " " + offset);

		// 改动区域
		Integer[] alist;
		alist = ssd.getScore().subList(offset * size, (offset + 1) * size).toArray(new Integer[0]);
		// 测试后 以下代码 耗时最长。
//		int[] alist = new int[size];
//		for (int i = 0; i < size; i++) {
//			alist[i] = ssd.getScore().indexOf(offset * size + i);
//		}
		Arrays.sort(alist);

		for (int i = 0; i < SimpleShareData.BUFSIZE * size / SimpleShareData.COUNT; i++) {
			//System.out.println("随机数: " + alist[alist.length - i - 1]);
			ssd.addExchange(alist[alist.length - i - 1]);
		}
		ssd.getCompSig().countDown();;
		//System.out.println("计算随机数完毕: " + size + " " + SimpleShareData.BUFSIZE * size / SimpleShareData.COUNT);
	}
}
