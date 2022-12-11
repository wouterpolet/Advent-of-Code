use crate::DaySolver;

pub struct Day11;

struct Monkey {
    _id: i32,
    _inspect: i32,
    _items: Vec<i32>,
    _op: String,
    _left_op: Option<i32>,
    _right_op: Option<i32>,
    _div_by: i32,
    _monkey_t: i32,
    _monkey_f: i32
}

impl Monkey {
    fn parse(inp: &str) -> Monkey {
        let mut lines = inp.lines();
        let id: i32 = lines.next().unwrap().split_once(" ").unwrap().1.strip_suffix(":").unwrap().parse().unwrap();
        let items: Vec<i32> = lines.next().unwrap().split_once(": ").unwrap().1.split(", ").map(|it| it.parse().unwrap()).collect();
        let exp: Vec<&str> = lines.next().unwrap().split_once(" = ").unwrap().1.split(" ").collect();
        let left: Option<i32> = exp[0].parse().map_or_else(|_| None, |x| Some(x));
        let right: Option<i32> = exp[2].parse().map_or_else(|_| None, |x| Some(x));
        let op: String = exp[1].to_string();
        let div_by: i32 = lines.next().unwrap().split(" ").last().unwrap().parse().unwrap();
        let monkey_t: i32 = lines.next().unwrap().split(" ").last().unwrap().parse().unwrap();
        let monkey_f: i32 = lines.next().unwrap().split(" ").last().unwrap().parse().unwrap();
        Monkey { 
            _id: id,
            _inspect: 0,
            _items: items,
            _op: op,
            _left_op: left,
            _right_op: right,
            _div_by: div_by,
            _monkey_t: monkey_t,
            _monkey_f: monkey_f
        }
    }

    // fn do_round(&mut self, m: &mut Vec<Monkey>) {
    //     for item in self.items.iter() {}
    //         let new_num: i32 = match self.op {
    //             "*" => self.left_op.unwrap_or(*item),
                
    //         }
    //     }
    // }
}

impl DaySolver for Day11 {
    fn solve_part1(&self, input: &str) -> i32 {
        let _monkeys: Vec<Monkey> = input.split("\n\n").map(|inp| Monkey::parse(inp)).collect();

        todo!()
    }

    fn solve_part2(&self, input: &str) -> i32 {
        input.len() as i32
    }
}