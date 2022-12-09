use std::collections::HashSet;

use crate::DaySolver;

pub struct Day09;

fn get_new_tail(head: (i32, i32), tail: (i32, i32)) -> (i32, i32) {
    if head.0.abs_diff(tail.0) <= 1 && head.1.abs_diff(tail.1) <= 1 {
        tail
    } else {
        if head.0 == tail.0 {
            if tail.1 < head.1 {
                (tail.0, tail.1 + 1)
            } else {
                (tail.0, tail.1 - 1)
            }
        } else if head.1 == tail.1 {
            if tail.0 < head.0 {
                (tail.0 + 1, tail.1)
            } else {
                (tail.0 - 1, tail.1)
            }
        } else {
            let a = if tail.0 < head.0 { tail.0 + 1} else { tail.0 - 1 };
            let b = if tail.1 < head.1 { tail.1 + 1} else { tail.1 - 1 };
            (a, b)
        }
    }
}

impl DaySolver for Day09 {
    fn solve_part1(&self, input: &str) -> i32 {
        let mut positions: HashSet<(i32, i32)> = HashSet::new();
        let mut head = (0, 0);
        let mut tail = (0, 0);
        positions.insert(tail.clone());

        input.lines().for_each(|l| {
            let (dir, num) = l.split_once(" ").unwrap();
            for _ in 0..num.parse::<i32>().unwrap() {
                match dir {
                    "R" => head = (head.0 + 1, head.1),
                    "U" => head = (head.0, head.1 + 1),
                    "L" => head = (head.0 - 1, head.1),
                    "D" => head = (head.0, head.1 - 1),
                    _ => panic!("Kapot")
                }
                tail = get_new_tail(head, tail);
                positions.insert(tail);
            }
        });

        positions.len() as i32
    }

    fn solve_part2(&self, input: &str) -> i32 {
        let mut positions: HashSet<(i32, i32)> = HashSet::new();
        let mut head = (0, 0);
        let mut tails: Vec<(i32, i32)> = Vec::new();
        (0..9).for_each(|_| tails.push((0, 0)));

        input.lines().for_each(|l| {
            let (dir, num) = l.split_once(" ").unwrap();
            for _ in 0..num.parse::<i32>().unwrap() {
                match dir {
                    "R" => head = (head.0 + 1, head.1),
                    "U" => head = (head.0, head.1 + 1),
                    "L" => head = (head.0 - 1, head.1),
                    "D" => head = (head.0, head.1 - 1),
                    _ => panic!("Kapot")
                }
                for i in 0..tails.len() {
                    if i == 0 {
                        tails[i] = get_new_tail(head, tails[i]);
                    } else {
                        tails[i] = get_new_tail(tails[i-1], tails[i]);
                    }
                }
                positions.insert(*tails.get(tails.len() - 1).unwrap());
            }
        });

        positions.len() as i32
    }
}