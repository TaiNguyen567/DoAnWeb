-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 02, 2026 at 10:26 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `habit_tracker_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `habits`
--

CREATE TABLE `habits` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `goal_days_per_week` int(11) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `goal` int(11) NOT NULL,
  `days_of_week` varchar(255) DEFAULT NULL,
  `time_frame` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `habits`
--

INSERT INTO `habits` (`id`, `created_at`, `description`, `goal_days_per_week`, `name`, `user_id`, `goal`, `days_of_week`, `time_frame`) VALUES
(1, NULL, 'Đọc 20 trang sách mỗi ngày', NULL, 'Đọc sách', 2, 7, NULL, NULL),
(7, NULL, '', NULL, 'Đọc sách', 4, 4, NULL, NULL),
(8, NULL, '', NULL, 'tập code', 4, 4, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `habit_checkins`
--

CREATE TABLE `habit_checkins` (
  `id` bigint(20) NOT NULL,
  `checkin_date` date NOT NULL,
  `notes` text DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `habit_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `habit_checkins`
--

INSERT INTO `habit_checkins` (`id`, `checkin_date`, `notes`, `status`, `habit_id`) VALUES
(13, '2026-05-30', '', 'COMPLETED', 7),
(14, '2026-06-02', '', 'COMPLETED', 8);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `created_at`, `email`, `password`, `username`) VALUES
(1, '2026-04-10 07:53:53.000000', 'nguyenvana@gmail.com', '$2a$10$FIwmDu6X5f1EJtDrSGBq2ObUFLL.5.y0DmUwxqbI8Tz82F0Bf6obm', 'nguyenvana'),
(2, '2026-04-17 09:06:59.000000', 'vana2@gmail.com', '$2a$10$6r5ksPbE8hqQ4Xyr/n3aHOfQ4CHhJAKcfHG3Qcz2vD/Z0Zmnyt8EG', 'nguyenvana2'),
(3, '2026-05-13 18:17:26.000000', 'test@gmail.com', '$2a$10$NCuZwFJ0tHDFDxgI9SaLk.vNkv/5cNQaCjlVzPpDlhlguHS.W.yMi', 'testuser'),
(4, '2026-05-23 14:04:54.000000', 'tainguyen@gmail.com', '$2a$10$zVBq7x81Ql.nPwkOTHeCLuphPmD6ZkQXX5bu152pnDB1X6wca4VEa', 'tainguyen');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `habits`
--
ALTER TABLE `habits`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKg3n2qqwmsyv3517xdcosouk9i` (`user_id`);

--
-- Indexes for table `habit_checkins`
--
ALTER TABLE `habit_checkins`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK58amnre0jbfmqwb7u2f8fwa50` (`habit_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  ADD UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `habits`
--
ALTER TABLE `habits`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `habit_checkins`
--
ALTER TABLE `habit_checkins`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `habits`
--
ALTER TABLE `habits`
  ADD CONSTRAINT `FKg3n2qqwmsyv3517xdcosouk9i` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `habit_checkins`
--
ALTER TABLE `habit_checkins`
  ADD CONSTRAINT `FK58amnre0jbfmqwb7u2f8fwa50` FOREIGN KEY (`habit_id`) REFERENCES `habits` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
