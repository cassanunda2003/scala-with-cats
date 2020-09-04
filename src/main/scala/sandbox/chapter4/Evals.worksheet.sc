import cats.Eval

val now = Eval.now(math.random + 1000)

val always = Eval.always(math.random + 3000)

val later = Eval.later(math.random + 2000)

now.value

always.value

later.value

val greeting = Eval.always{print("Step 1"); "Hello"}
    .map{str => println("Step 2"); s"$str world"}

greeting.value