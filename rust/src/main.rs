extern crate core;

use std::{env, fs};

mod aoc2015;
mod aoc2022;

pub trait DaySolver {
    fn solve_part1_str(&self, input: &str) -> String {
        self.solve_part1_i64(input).to_string()
    }
    fn solve_part2_str(&self, input: &str) -> String {
        self.solve_part2_i64(input).to_string()
    }

    fn solve_part1_i64(&self, input: &str) -> i64 {
        self.solve_part1(input) as i64
    }
    fn solve_part2_i64(&self, input: &str) -> i64 {
        self.solve_part2(input) as i64
    }

    fn solve_part1(&self, _input: &str) -> i32 {
        panic!("No solution has been implemented for part one.")
    }
    fn solve_part2(&self, _input: &str) -> i32 {
        panic!("No solution has been implemented for part two.")
    }
}

trait YearSolver {
    fn get_day_solver(&self, day: &i32) -> Option<&dyn DaySolver>;

    fn solve(&self, day: &i32, part: &i32, input: &str) -> Option<String> {
        match self.get_day_solver(day) {
            Some(day_solver) => match part {
                1 => Some(day_solver.solve_part1_str(input)),
                2 => Some(day_solver.solve_part2_str(input)),
                _ => None
            }
            None => None
        }
    }
}

fn get_year_solver(year: &i32) -> Option<&dyn YearSolver> {
    match year {
        2015 => Some(&aoc2015::Year2015),
        2022 => Some(&aoc2022::Year2022),
        _ => None
    }
}

fn solve(year: &i32, day: &i32, part: &i32) {
    let path = format!("data/year{year}/day{}{day}/input.txt", if *day < 10 { "0" } else { "" });
    let file_result = fs::read_to_string(path);
    match file_result {
        Ok(input) => match get_year_solver(year).and_then(|s| s.solve(day, part, &*input)) {
            Some(answer) => println!("Answer for year {year}, day {day}, part {part}: {answer}"),
            None => println!("Day {day} of year {year} has no implementation yet.")
        },
        Err(e) => println!("Could not read input because: {e}")
    };
}

fn main() {
    let args: Vec<String> = env::args().collect();

    if args.len() < 3 {
        println!("Expected at least two arguments (year and day), but got {}", args.len() - 1);
        return;
    }

    match [&args.get(1), &args.get(2), &args.get(3)].map(|arg| arg.unwrap_or(&"".to_string()).parse::<i32>()) {
        [Ok(year), Ok(day), Ok(part)] => {
            println!("Running part {part} of day {day} in {year}");
            solve(&year, &day, &part);
        },
        [Ok(year), Ok(day), _] => {
            println!("Running both parts of day {day} in {year}");
            solve(&year, &day, &1);
            solve(&year, &day, &2);
        },
        [Err(_), Err(_), _] => println!("Year and day are both not integers"),
        [Err(_), _, _] => println!("Year is not an integer."),
        [_, Err(_), _] => println!("Day is not an integer.")
    }
}
