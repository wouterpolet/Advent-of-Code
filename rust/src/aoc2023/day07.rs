use std::cmp::Ordering;

use itertools::Itertools;

use crate::DaySolver;

pub struct Day07;

impl Day07 {
    fn cmp_hands(h1: &Vec<i32>, h2: &Vec<i32>) -> Ordering {
        let rank1 = Day07::find_rank(h1);
        let rank2 = Day07::find_rank(h2);

        if rank1 == rank2 {
            h1.cmp(h2)
        } else {
            rank1.cmp(&rank2)
        }
    }

    fn find_rank(hand: &Vec<i32>) -> i32 {
        if Day07::five_of_kind(hand) {
            6
        } else if Day07::four_of_kind(hand) {
            5
        } else if Day07::full_house(hand) {
            4
        } else if Day07::three_of_kind(hand) {
            3
        } else if Day07::two_pair(hand) {
            2
        } else if Day07::one_pair(hand) {
            1
        } else {
            0
        }
    }

    fn five_of_kind(hand: &Vec<i32>) -> bool {
        hand.into_iter().unique().any(|x| Day07::count(hand, x) + if x != &1 { Day07::count_jokers(hand) } else { 0 } == 5)
    }

    fn four_of_kind(hand: &Vec<i32>) -> bool {
        hand.into_iter().unique().any(|x| Day07::count(hand, x) + if x != &1 { Day07::count_jokers(hand) } else { 0 } == 4)
    }

    fn full_house(hand: &Vec<i32>) -> bool {
        (hand.into_iter().unique().any(|c| hand.into_iter().filter(|x| *x == c).count() == 2)
            && hand.into_iter().unique().any(|c| hand.into_iter().filter(|x| *x == c).count() == 3))
        || (Day07::count_jokers(hand) == 1 && hand.into_iter().unique().filter(|c| hand.into_iter().filter(|x| x == c).count() == 2).count() == 2)
        || (Day07::count_jokers(hand) == 2 && hand.into_iter().unique().filter(|c| hand.into_iter().filter(|x| x == c).count() == 2).count() == 2)
    }

    fn three_of_kind(hand: &Vec<i32>) -> bool {
        hand.into_iter().unique().any(|x| Day07::count(hand, x) + if x != &1 { Day07::count_jokers(hand) } else { 0 } == 3)
    }

    fn two_pair(hand: &Vec<i32>) -> bool {
        hand.into_iter().unique().filter(|c| hand.into_iter().filter(|x| x == c).count() == 2).count() == 2
        || (Day07::count_jokers(hand) == 1 && hand.into_iter().unique().filter(|c| hand.into_iter().filter(|x| x == c).count() == 2).count() == 1)
    }

    fn one_pair(hand: &Vec<i32>) -> bool {
        hand.into_iter().unique().any(|x| Day07::count(hand, x) + if x != &1 { Day07::count_jokers(hand) } else { 0 } == 2)
    }

    fn count_jokers(hand: &Vec<i32>) -> i32 {
        hand.into_iter().filter(|x| **x == 1).count() as i32
    }

    fn count(hand: &Vec<i32>, num: &i32) -> i32 {
        hand.into_iter().filter(|x| *x == num).count() as i32
    }
}

impl DaySolver for Day07 {
    fn solve_part1(&self, input: &str) -> i32 {
        let mut hands = input.lines().map(|l| {
            let (cards, bet) = l.split_once(" ").unwrap();

            (
                cards.chars().map(|c| match c {
                    'A' => 14,
                    'K' => 13,
                    'Q' => 12,
                    'J' => 11,
                    'T' => 10,
                    _ => c.to_digit(10).unwrap() as i32
                }).collect(),
                bet.parse::<i32>().unwrap()
            )
        }).collect::<Vec<(Vec<i32>, i32)>>();

        hands.sort_by(|(a, _), (b, _)| Day07::cmp_hands(a, b));

        hands.into_iter().enumerate().map(|(i, (_, bet))| (i as i32 + 1) * bet).sum()
    }

    fn solve_part2(&self, input: &str) -> i32 {
        let mut hands = input.lines().map(|l| {
            let (cards, bet) = l.split_once(" ").unwrap();

            (
                cards.chars().map(|c| match c {
                    'A' => 14,
                    'K' => 13,
                    'Q' => 12,
                    'J' => 1,
                    'T' => 10,
                    _ => c.to_digit(10).unwrap() as i32
                }).collect(),
                bet.parse::<i32>().unwrap()
            )
        }).collect::<Vec<(Vec<i32>, i32)>>();

        hands.sort_by(|(a, _), (b, _)| Day07::cmp_hands(a, b));

        hands.into_iter().enumerate().map(|(i, (_, bet))| (i as i32 + 1) * bet).sum()
    }
}
