use crate::DaySolver;

pub struct Day01;

impl DaySolver for Day01 {
    fn solve_part1(&self, input: &str) -> i32 {
        input.split("\n\n")
            .map(|elf| elf.split("\n")
                .map(|food| food.parse::<i32>().unwrap())
                .sum::<i32>())
            .max()
            .unwrap()
    }

    fn solve_part2(&self, input: &str) -> i32 {
        let mut calories = input.split("\n\n")
            .map(|elf| elf.split("\n")
                .map(|food| food.parse::<i32>().unwrap())
                .sum::<i32>())
            .collect::<Vec<i32>>();
        calories.sort();
        calories[calories.len()-3..calories.len()]
            .iter()
            .sum::<i32>()
    }
}