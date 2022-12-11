use crate::{DaySolver, YearSolver};

mod day01;
mod day11;
mod day12;
mod day13;

pub struct Year2015;

impl YearSolver for Year2015 {
    fn get_day_solver(&self, day: &i32) -> Option<&dyn DaySolver> {
        match day {
            1 => Some(&day01::Day01),
            11 => Some(&day11::Day11),
            12 => Some(&day12::Day12),
            13 => Some(&day13::Day13),
            _ => None
        }
    }
}
