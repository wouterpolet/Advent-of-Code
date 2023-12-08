use std::collections::{HashSet, HashMap};

use crate::DaySolver;

pub struct Day04;


impl DaySolver for Day04 {
    fn solve_part1(&self, input: &str) -> i32 {
        input.lines().map(|l| {
            let (_, nums) = l.split_once(": ").unwrap();
            let (winning, own) = nums.split_once(" | ").unwrap();

            let winning_nums = winning.split_ascii_whitespace().filter(|x| !x.is_empty()).collect::<HashSet<&str>>();
            let own_nums = own.split_ascii_whitespace().filter(|x| !x.is_empty()).collect::<HashSet<&str>>();

            let num_cards = winning_nums.intersection(&own_nums).collect::<Vec<&&str>>().len() as u32;

            if num_cards > 0 {
                (2 as i32).pow(num_cards - 1)
            } else {
                0
            }
        }).sum()
    }

    fn solve_part2(&self, input: &str) -> i32 {
        let mut cards = HashMap::new();
        cards.insert(0, 1);
        
        input.lines().enumerate().for_each(|(index, l)| {
            if !cards.contains_key(&index) {
                cards.insert(index, 1);
            }

            let (_, nums) = l.split_once(": ").unwrap();
            let (winning, own) = nums.split_once(" | ").unwrap();

            let winning_nums = winning.split_ascii_whitespace().filter(|x| !x.is_empty()).collect::<HashSet<&str>>();
            let own_nums = own.split_ascii_whitespace().filter(|x| !x.is_empty()).collect::<HashSet<&str>>();
            let num_cards = winning_nums.intersection(&own_nums).collect::<Vec<&&str>>().len();

            (index+1..=index+num_cards).for_each(|card_index| {
                cards.insert(card_index, *cards.get(&card_index).unwrap_or(&1) + cards.get(&index).unwrap_or(&1));
            });
        });

        cards.values().sum()
    }
}
