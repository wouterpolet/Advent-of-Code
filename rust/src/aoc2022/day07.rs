use std::collections::HashMap;

use crate::DaySolver;

pub struct Day07;

fn path_to_str(path: &Vec<String>) -> String {
    path.join("/")
}

fn get_files_from(path: String, children: &HashMap<String, Vec<String>>) -> Vec<String> {
    match children.get(&path) {
        Some(sub_dirs) => sub_dirs.iter().flat_map(|sub_dir| get_files_from(String::from(sub_dir), children)).collect(),
        None => vec![path]
    }
}

fn parse(input: &str) -> (HashMap<String, Vec<String>>, HashMap<String, i64>) {
    let mut path: Vec<String> = Vec::new();
        let mut sizes: HashMap<String, i64> = HashMap::new();
        let mut children: HashMap<String, Vec<String>> = HashMap::new();
        children.insert(path_to_str(&vec![String::from("/")]), Vec::new());

        let mut lines = input.lines().peekable();

        while lines.peek().is_some() {
            let line = lines.next().unwrap();
                       
            match line {
                "$ ls" => {
                    while !lines.peek().unwrap_or(&"$").starts_with("$") {
                        let node = lines.next().unwrap();
                        if !node.starts_with("dir") {
                            let (size_str, name) = node.split_once(" ").unwrap();
                            children.get_mut(&path_to_str(&path)).unwrap().push(path_to_str(&path).to_owned() + "/" + name);
                            sizes.insert(path_to_str(&path) + "/" + name, sizes.get(&path_to_str(&path)).unwrap_or(&0) + size_str.parse::<i64>().unwrap());
                        }
                    }
                }
                cd if cd.starts_with("$ cd ") => {
                    let (_, new_dir) = cd.split_once(" cd ").unwrap();
                    match new_dir {
                        ".." => { path.pop(); },
                        "/" => path = vec![String::from("/")],
                        _ => {
                            children.get_mut(&path_to_str(&path)).unwrap().push(path_to_str(&path).to_owned() + "/" + new_dir);
                            path.push(new_dir.to_string());
                            children.insert(path_to_str(&path), Vec::new());
                        }
                    };                    
                    
                }
                _ => panic!("Not a command {}", line)
            }
        }

        (children, sizes)
}

impl DaySolver for Day07 {
    fn solve_part1_i64(&self, input: &str) -> i64 {
        let (children, sizes) = parse(input);

        children
            .keys()
            .map(|dir| get_files_from(String::from(dir), &children).iter().map(|f| sizes.get(f).unwrap()).sum())
            .filter(|s: &i64| *s <= 100000)
            .sum()
    }

    fn solve_part2_i64(&self, input: &str) -> i64 {
        let (children, sizes) = parse(input);

        let total_space = 70000000;
        let free_needed = 30000000;
        let maximum = total_space - free_needed;

        let current: i64 = get_files_from(String::from("/"), &children).iter().map(|f| sizes.get(f).unwrap()).sum();
        let to_free = current - maximum;

        children
            .keys()
            .map(|dir| get_files_from(String::from(dir), &children).iter().map(|f| sizes.get(f).unwrap()).sum())
            .filter(|s: &i64| *s >= to_free)
            .min()
            .unwrap()
    }
}
