## 使用lambda表达式建立子线程任务并阻塞主线程
```java
// 阻塞主线程的计数器
        CountDownLatch countDownLanch = new CountDownLatch(cycleNum);
       	// 局部的线程池
        ExecutorService executor = Executors.newFixedThreadPool(cycleNum > 4 ? 4 : cycleNum);
      	// cycleNum是要执行子线程的次数
        for (int i = 0; i < cycleNum; i++) {
            int start = i * 10000;
            int num = 10000;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<Map<String, Double>> dataListTemp = dataShowMapper.getWaveEchartsCorrectDataNoSparse(csrwId, xb, "0", "0", start, num);
                        for (Map<String, Double> dataMap : dataListTemp) {
                            ZSetOperations.TypedTuple<Map<String, Double>> typedTuple = new DefaultTypedTuple<>(dataMap, dataMap.get("kms"));
                            tuples.add(typedTuple);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
					// 每执行一次子线程（计数器减一）
                   		countDownLanch.countDown();
                    }

                }
            });
        }

        try {
            // 阻塞主线程
            countDownLanch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
      	// 子线程都执行完后，关闭局部线程池
        executor.shutdown();
```