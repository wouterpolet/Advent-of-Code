use crate::{DaySolver, YearSolver};

mod day01;
mod day02;
mod day03;

pub struct Year2022;

impl YearSolver for Year2022 {
    fn get_day_solver(&self, day: &i32) -> Option<&dyn DaySolver> {
        match day {
            1 => Some(&day01::Day01),
            2 => Some(&day02::Day02),
            3 => Some(&day03::Day03),
            _ => None
        }
    }
}
