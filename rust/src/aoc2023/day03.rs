use std::collections::HashMap;

use crate::DaySolver;

pub struct Day03;

impl DaySolver for Day03 {
    fn solve_part1(&self, input: &str) -> i32 {
        let mut characters: HashMap<(i32, i32), char> = HashMap::new();
        input.lines().enumerate().for_each(|(row_index, row)| {
            row.char_indices().for_each(|(col_index, character)| {
                if !character.is_ascii_digit() && character != '.' {
                    characters.insert((row_index as i32, col_index as i32), character);
                }
            })
        });

        let mut res = 0;
        let mut start_col = -1;
        let mut acc = 0;

        input.lines().enumerate().for_each(|(row_index, row)| {
            row.char_indices().for_each(|(col_index, entry)| {
                if entry.is_ascii_digit() {
                    if start_col < 0 {
                        start_col = col_index as i32
                    }

                    acc = acc * 10 + entry.to_digit(10).unwrap();
                } else if start_col >= 0 {
                    // Check if adjacent
                    if (start_col-1..=col_index as i32).any(|c| characters.contains_key(&(row_index as i32, c))
                            || characters.contains_key(&(row_index as i32 - 1, c))
                            || characters.contains_key(&(row_index as i32 + 1, c))) {
                        res += acc; 
                    }
                    acc = 0;
                    start_col = -1;
                }
            });

            if start_col >= 0 && (start_col-1..=row.len() as i32).any(|c| characters.contains_key(&(row_index as i32, c))
                    || characters.contains_key(&(row_index as i32 - 1, c))
                    || characters.contains_key(&(row_index as i32 + 1, c))) {
                res += acc;
            }

            acc = 0;
            start_col = -1;
        });

        res as i32
    }

    fn solve_part2(&self, input: &str) -> i32 {
        let mut characters: HashMap<(i32, i32), char> = HashMap::new();
        input.lines().enumerate().for_each(|(row_index, row)| {
            row.char_indices().for_each(|(col_index, character)| {
                if character == '*' {
                    characters.insert((row_index as i32, col_index as i32), character);
                }
            })
        });

        println!("chars: {:?}", characters);

        let mut part_nums: HashMap<(i32, i32), (i32, i32)> = HashMap::new();
        let mut start_col = -1;
        let mut acc: i32 = 0;
        input.lines().enumerate().for_each(|(row_index, row)| {
            row.char_indices().for_each(|(col_index, entry)| {
                if entry.is_ascii_digit() {
                    if start_col < 0 {
                        start_col = col_index as i32
                    }

                    acc = acc * 10 + entry.to_digit(10).unwrap() as i32;
                } else if start_col >= 0 {
                    // Check if adjacent
                    (start_col-1..=col_index as i32).for_each(|c| {

                        if characters.contains_key(&(row_index as i32, c)) {
                            let pos = &(row_index as i32, c);
                            let (old_count, old_acc) = part_nums.get(pos).unwrap_or(&(0, 1));
                            println!("1. adding {:?} to {:?}", acc, pos);
                            part_nums.insert(*pos, (old_count + 1, old_acc * acc));
                        }
                        if characters.contains_key(&(row_index as i32 - 1, c)) {
                            let pos = &(row_index as i32 - 1, c);
                            let (old_count, old_acc) = part_nums.get(pos).unwrap_or(&(0, 1));
                            println!("2. adding {:?} to {:?}", acc, pos);
                            part_nums.insert(*pos, (old_count + 1, old_acc * acc));
                        }
                        if characters.contains_key(&(row_index as i32 + 1, c)) {
                            let pos = &(row_index as i32 + 1, c);
                            let (old_count, old_acc) = part_nums.get(pos).unwrap_or(&(0, 1));
                            println!("3. adding {:?} to {:?}", acc, pos);
                            part_nums.insert(*pos, (old_count + 1, old_acc * acc));
                        }

                    });

                    acc = 0;
                    start_col = -1;
                }
            });
            
            if start_col >= 0 {
                (start_col-1..=row.len() as i32).for_each(|c| {

                    if characters.contains_key(&(row_index as i32, c)) {
                        let pos = &(row_index as i32, c);
                        let (old_count, old_acc) = part_nums.get(pos).unwrap_or(&(0, 1));
                        println!("4. adding {:?} to {:?}", acc, pos);
                        part_nums.insert(*pos, (old_count + 1, old_acc * acc));
                    }
                    if characters.contains_key(&(row_index as i32 - 1, c)) {
                        let pos = &(row_index as i32 - 1, c);
                        let (old_count, old_acc) = part_nums.get(pos).unwrap_or(&(0, 1));
                        println!("5. adding {:?} to {:?}", acc, pos);
                        part_nums.insert(*pos, (old_count + 1, old_acc * acc));
                    }
                    if characters.contains_key(&(row_index as i32 + 1, c)) {
                        let pos = &(row_index as i32 + 1, c);
                        let (old_count, old_acc) = part_nums.get(pos).unwrap_or(&(0, 1));
                        println!("6. adding {:?} to {:?}", acc, pos);
                        part_nums.insert(*pos, (old_count + 1, old_acc * acc));
                    }
                    
                });
            }

            acc = 0;
            start_col = -1;
        });

        println!("part_nums: {:?}", part_nums);

        part_nums.values().filter(|(count, _)| *count == 2).map(|(_, acc)| acc).sum()
    }
}

