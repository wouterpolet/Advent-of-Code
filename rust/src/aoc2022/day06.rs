use std::collections::HashSet;
use crate::DaySolver;

pub struct Day06;

impl DaySolver for Day06 {
    fn solve_part1(&self, input: &str) -> i32 {
        for i in 0..input.len() {
            if input[i..i+4].chars().collect::<HashSet<char>>().len() == 4 {
                return (i + 4) as i32
            }
        }

        -1
    }

    fn solve_part2(&self, input: &str) -> i32 {
        for i in 0..input.len() {
            if input[i..i+14].chars().collect::<HashSet<char>>().len() == 14 {
                return (i + 14) as i32
            }
        }

        -1
    }
}