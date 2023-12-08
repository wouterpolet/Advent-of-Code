use crate::{DaySolver, YearSolver};

mod day01;
mod day02;
mod day03;
mod day04;
mod day05;
mod day06;
mod day07;

pub struct Year2023;

impl YearSolver for Year2023 {
    fn get_day_solver(&self, day: &i32) -> Option<&dyn DaySolver> {
        match day {
            1 => Some(&day01::Day01),
            2 => Some(&day02::Day02),
            3 => Some(&day03::Day03),
            4 => Some(&day04::Day04),
            5 => Some(&day05::Day05),
            6 => Some(&day06::Day06),
            7 => Some(&day07::Day07),
            _ => None
        }
    }
}
