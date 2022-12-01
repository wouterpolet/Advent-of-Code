use crate::DaySolver;

pub struct Day01;

impl DaySolver for Day01 {
    fn solve_part1(&self, input: &str) -> i32 {
        (input.chars().filter(|c| *c == '(').count() -
            input.chars().filter(|c| *c == ')').count()) as i32
    }

    fn solve_part2(&self, input: &str) -> i32 {
        input.chars().fold((0, 0), |(floor, index), c|
            if floor == -1 { (floor, index) } else {
                (floor + if c == '(' { 1 } else { -1 }, index + 1)
            }
        ).1
    }
}