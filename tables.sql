-- notebook.notebookinfo definition

CREATE TABLE `notebookinfo` (
  `id` int NOT NULL,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `abs` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lastChangeTime` timestamp NULL DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL,
  `firstImageName` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `userId` int NOT NULL,
  `label` int DEFAULT '0',
  PRIMARY KEY (`id`,`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- notebook.userinfo definition

CREATE TABLE `userinfo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb3 DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb3 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `textpojoimp` (
  `id` int NOT NULL,
  `headlineContent` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
  `headlineSpan` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
  `textSpan` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
  `textContent` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
  `time` timestamp NULL DEFAULT NULL,
  `imgSrc` varchar(700) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `tag` int DEFAULT NULL,
  `userId` int NOT NULL,
  PRIMARY KEY (`id`,`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;