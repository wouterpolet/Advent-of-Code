use std::collections::HashSet;
use crate::DaySolver;

pub struct Day11;
// Start: vzbxkghb
// wrong: vzccdeaa
// wrong: vzccaabc
// wrong: vzcaabcc
// right: vzbxxyzz
// next one right: vzcaabcc

impl Day11 {
    fn get_chunks(&self, password: &str, size: usize) -> Vec<Vec<char>> {
        let mut result: Vec<Vec<char>> = Vec::new();

        for i in 0..password.len() - size + 1 {
            let mut chunk = vec!['a'; size];
            for x in 0..size {
                chunk[x] = password.chars().nth(i + x).unwrap();
            }
            result.push(chunk);
        }

        result
    }

    fn increment_password(&self, password: String) -> String {
        password.chars().rfold(("".to_owned(), true), |(acc, inc), c| {
            let char_code = (c as u32) + (inc as u32);
            let inc_next = char_code > ('z' as u32);
            let new_char = if !inc_next { char::from_u32(char_code).unwrap() } else { 'a' };

            (new_char.to_string() + &*acc, inc_next)
        }).0
    }

    fn check_password(&self, password: &str) -> bool {
        self.get_chunks(password, 3)
            .iter()
            .any(|chunk| {
                chunk[0] as u32 + 1 == chunk[1] as u32 && chunk[0] as u32 + 2 == chunk[2] as u32
            })
        && !(password.contains('i') || password.contains('o') || password.contains('l'))
        && self.get_chunks(password, 2)
            .iter()
            .filter_map(|chunk| if chunk[0] == chunk[1] { Some(chunk[0]) } else { None })
            .collect::<HashSet<char>>()
            .len() >= 2
    }
}

impl DaySolver for Day11 {
    fn solve_part1_str(&self, input: &str) -> String {
        let mut password: String = input.to_string();
        loop {
            password = self.increment_password(password);
            if self.check_password(&password) {
                break password
            }
        }
    }

    fn solve_part2_str(&self, input: &str) -> String {
        self.solve_part1_str(&*self.solve_part1_str(input))
    }
}