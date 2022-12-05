use std::collections::VecDeque;
use crate::DaySolver;

pub struct Day05;

impl Day05 {
    fn parse(&self, input: &str) -> (Vec<VecDeque<char>>, Vec<(i32, usize, usize)>) {
        let (stacks_init, ops_str) = input.split_once("\n\n").unwrap();
        let mut stacks: Vec<VecDeque<char>> = stacks_init.lines().last().unwrap().split("   ")
            .map(|_| VecDeque::new()).collect();

        let mut index = 0;
        stacks_init.lines().for_each(|line| {
            let mut iterator = line.chars();
            loop {
                match iterator.next() {
                    Some('[') => {
                        stacks.get_mut(index).unwrap().push_front(iterator.next().unwrap());
                        iterator.next();
                        iterator.next();
                    }
                    Some(' ') => {
                        (1..=3_i32).for_each(|_| { iterator.next(); });
                    }
                    _ => {
                        index = 0;
                        break;
                    }
                }
                index += 1
            }
        });

        let ops = ops_str.lines().map(|op| {
            let (num_str, locations) = op.split_once(" from ").unwrap();
            let num: i32 = num_str.strip_prefix("move ").unwrap().parse().unwrap();
            let (origin_str, dest_str) = locations.split_once(" to ").unwrap();
            let origin: usize = origin_str.parse::<usize>().unwrap() - 1;
            let dest: usize = dest_str.parse::<usize>().unwrap() - 1;

            (num, origin, dest)
        }).collect();


        (stacks, ops)
    }
}

impl DaySolver for Day05 {
    fn solve_part1_str(&self, input: &str) -> String {
        let (mut stacks, ops) = self.parse(input);

        ops.iter().for_each(|(num, origin, dest)| {
            for _ in 0..*num {
                let to_add = stacks.get_mut(*origin).unwrap().pop_back().unwrap();
                stacks.get_mut(*dest).unwrap().push_back(to_add);
            }
        });

        stacks.iter_mut().map(|stack| stack.pop_back().unwrap()).collect()
    }

    fn solve_part2_str(&self, input: &str) -> String {
        let (mut stacks, ops) = self.parse(input);

        ops.iter().for_each(|(num, origin, dest)| {
            let mut temp = VecDeque::new();
            for _ in 0..*num {
                temp.push_back(stacks.get_mut(*origin).unwrap().pop_back().unwrap());
            }
            while !temp.is_empty() {
                stacks.get_mut(*dest).unwrap().push_back(temp.pop_back().unwrap());
            }
        });

        stacks.iter_mut().map(|stack| stack.pop_back().unwrap()).collect()
    }
}