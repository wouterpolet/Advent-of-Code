use crate::DaySolver;

pub struct Day02;

// Rock
// Paper
// Scissors

impl DaySolver for Day02 {
    fn solve_part1(&self, input: &str) -> i32 {
        input.lines().map(|line| {
            let (x, y) = line.split_once(" ").unwrap();
            match x {
                "A" => match y {
                    "X" => 4,
                    "Y" => 8,
                    "Z" => 3,
                    _ => panic!("")
                }
                "B" => match y {
                    "X" => 1,
                    "Y" => 5,
                    "Z" => 9,
                    _ => panic!("")
                },
                "C" => match y {
                    "X" => 7,
                    "Y" => 2,
                    "Z" => 6,
                    _ => panic!("")
                },
                _ => panic!("")
            }
        }).sum()
    }

    fn solve_part2(&self, input: &str) -> i32 {
        input.lines().map(|line| {
            let (x, y) = line.split_once(" ").unwrap();
            match x {
                "A" => match y {
                    "X" => 3,
                    "Y" => 4,
                    "Z" => 8,
                    _ => panic!("")
                }
                "B" => match y {
                    "X" => 1,
                    "Y" => 5,
                    "Z" => 9,
                    _ => panic!("")
                },
                "C" => match y {
                    "X" => 2,
                    "Y" => 6,
                    "Z" => 7,
                    _ => panic!("")
                },
                _ => panic!("")
            }
        }).sum()
    }
}