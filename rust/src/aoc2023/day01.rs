use crate::DaySolver;

pub struct Day01;

const NUMBERS: [&str; 20] = [
    "zero",
    "one",
    "two",
    "three",
    "four",
    "five",
    "six",
    "seven",
    "eight",
    "nine",
    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
];

impl DaySolver for Day01 {
    fn solve_part1(&self, input: &str) -> i32 {
        input.lines().map(|l| -> i32 {
            l.chars().find(|c| c.is_numeric()).unwrap().to_digit(10).unwrap() as i32 * 10 +
                l.chars().rev().find(|c| c.is_numeric()).unwrap().to_digit(10).unwrap() as i32
        }).sum()
    }

    fn solve_part2(&self, input: &str) -> i32 {
        input.lines().map(|l| -> i32 {
            let indices = NUMBERS.map(|num|
                l.find(num).map(|x| x as i32).unwrap_or(-1)
            );
            let max_indices = NUMBERS.map(|num|
                l.match_indices(num).map(|x| x.0).max().map(|x| x as i32).unwrap_or(-1)
            );

            let min_num = indices.into_iter().filter(|x| *x > -1).min().unwrap();
            let max_num = max_indices.into_iter().max().unwrap();

            println!("{:?}{:?}", indices.into_iter().position(|x| x == min_num).unwrap() as i32 % 10, (max_indices.into_iter().position(|x| x == max_num).unwrap() as i32 % 10));

            (indices.into_iter().position(|x| x == min_num).unwrap() as i32 % 10) * 10 + (max_indices.into_iter().position(|x| x == max_num).unwrap() as i32 % 10)
        }).sum()
    }
}
