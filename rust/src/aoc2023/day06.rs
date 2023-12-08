use itertools::Itertools;

use crate::DaySolver;

pub struct Day06;

impl DaySolver for Day06 {
    fn solve_part1(&self, input: &str) -> i32 {
        let mut lines = input.lines();

        let times = lines.next().unwrap().split_ascii_whitespace().map(|x| x.parse::<i32>()).filter(|x| x.is_ok()).map(|x| x.unwrap());
        let distances = lines.next().unwrap().split_ascii_whitespace().map(|x| x.parse::<i32>()).filter(|x| x.is_ok()).map(|x| x.unwrap());

        times.into_iter().zip(distances.into_iter())
            .map(|(time, dist)| (1..time).filter(|hold_sec| hold_sec * (time - hold_sec) > dist).collect::<Vec<i32>>().len() as i32)
            .fold(1, |acc, x| acc * x)
    }

    fn solve_part2_i64(&self, input: &str) -> i64 {
        let mut lines = input.lines();

        let time = lines.next().unwrap().split_once(" ").unwrap().1.split_ascii_whitespace().join("").parse::<i64>().unwrap();
        let dist = lines.next().unwrap().split_once(" ").unwrap().1.split_ascii_whitespace().join("").parse::<i64>().unwrap();

        (1..time).filter(|hold_sec| hold_sec * (time - hold_sec) > dist).collect::<Vec<i64>>().len() as i64
    }
}
