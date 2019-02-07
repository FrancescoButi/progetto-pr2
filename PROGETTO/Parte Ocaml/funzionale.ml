type ide = string;;

type exp = 
	(* Tipi fondamentali *)
	Eint of int |
	Ebool of bool | 
	(* Denifizione di un valore *)
	Den of ide |
	(* Operazioni algebriche *)
	Prod of exp * exp | 
	Sum of exp * exp | 
	Diff of exp * exp |
	Minus of exp | 
	(* Operatori Booleani *)
	Eq of exp * exp | 
	IsZero of exp | 
	Or of exp * exp |
	And of exp * exp | 
	Not of exp |
	(* Istruzioni *)
	Ifthenelse of exp * exp * exp |
	(* Dichiarazioni *)
	Let of ide * exp * exp |
	(* Funzioni *)
	Fun of ide * exp |
	FunCall of exp * exp |
	Letrec of ide * exp * exp |
	(* dizionari e operazioni *)
	Dictionary of (ide * exp ) list |
	DictGet of ide * ide | 
	DictRm of ide * ide |
	DictClear of ide | 
	ApplyOver of (exp * exp) | 
	DictAdd of (ide * (ide * exp )) ;;
	(*Elemento di un dizionario *)
(*ambiente polimorfo*)
type 't env = ide -> 't;;
let emptyenv (v : 't) = function x -> v;;
let applyenv (r : 't env) (i : ide) = r i;;
let bind (r : 't env) (i : ide) (v : 't) = function x -> if x = i then v else applyenv r x;;
(*tipi esprimibili*)
type evT = 
	(* Ambiente vuoto *)
	Unbound |
	(* Tipi fondamentali *)
	Int of int |
	Bool of bool |  
	(* dizionario *)
	Dict of (ide * evT) list | 
	(* Funzioni *)
	FunVal of evFun | 
	RecFunVal of ide * evFun
and 
	(* Ambiente della funzione *)
	evFun = ide * exp * evT env;;
(*rts*)
(*type checking*)
let typecheck (s : string) (v : evT) : bool = match s with
	"int" -> (match v with
		Int(_) -> true |
		_ -> false) |
	"bool" -> (match v with
		Bool(_) -> true |
		_ -> false) |
	_ -> failwith("not a valid type");;


(*funzioni primitive*)
let prod x y = if (typecheck "int" x) && (typecheck "int" y)
	then (match (x,y) with
		(Int(n),Int(u)) -> Int(n*u)
		| _ -> failwith("Type error"))
	else failwith("Type error");;

let sum x y = if (typecheck "int" x) && (typecheck "int" y)
	then (match (x,y) with
		(Int(n),Int(u)) -> Int(n+u)
		| _ -> failwith("Type error") )
	else failwith("Type error");;

let diff x y = if (typecheck "int" x) && (typecheck "int" y)
	then (match (x,y) with
		(Int(n),Int(u)) -> Int(n-u)
		| _ -> failwith("Type error"))
	else failwith("Type error");;

let eq x y = if (typecheck "int" x) && (typecheck "int" y)
	then (match (x,y) with
		(Int(n),Int(u)) -> Bool(n=u)
		| _ -> failwith("Type error"))
	else failwith("Type error");;

let minus x = if (typecheck "int" x) 
	then (match x with
		 Int(n) -> Int(-n)
			 | _ -> failwith("Type error"))
	else failwith("Type error");;

let iszero x = if (typecheck "int" x)
	then (match x with
		Int(n) -> Bool(n=0)
		| _ -> failwith("Type error"))
	else failwith("Type error");;

let vel x y = if (typecheck "bool" x) && (typecheck "bool" y)
	then (match (x,y) with
		(Bool(b),Bool(e)) -> (Bool(b||e))
		| _ -> failwith("Type error"))
	else failwith("Type error");;

let et x y = if (typecheck "bool" x) && (typecheck "bool" y)
	then (match (x,y) with
		(Bool(b),Bool(e)) -> Bool(b&&e)
		| _ -> failwith("Type error"))

	else failwith("Type error");;

let non x = if (typecheck "bool" x)

	then (match x with
		Bool(true) -> Bool(false) |
		Bool(false) -> Bool(true)
		| _ -> failwith("Type error"))
	else failwith("Type error");;

(* cerca la chiave nel dizionario, se va a buon fine restituisce il valore associato *)
let rec search ( v : ide ) ( ls : (ide * evT) list ) = 
	match ls with 
		[] -> failwith("Value not found ") |
		(x,y)::xs -> if v =  x then y else search v xs;;

(* cerca la chiave e, se la trova, elimina il dato corrispondente *)
let rec delete ( v : ide ) ( ls : (ide * evT) list ) = 
	match ls with 
		[] -> [] |
		(x,y) :: xs -> if v =  x then delete v xs else (x,y) :: delete v xs ;;

(* ricerca la chiave nella lista, restituisce true se la trova *)
let rec is_in (v : ide ) (ls : (ide * evT) list ) = 
	match ls with 
		[] -> false |
		(x,y) :: xs -> if v = x then true else is_in v xs ;;

(*interprete*) 
let rec eval (e:exp) (r : evT env) : evT = (match e with
	Eint n -> Int n |
	Ebool b -> Bool b |
	IsZero a -> iszero (eval a r) |
	Den i -> applyenv r i |
	Eq(a, b) -> eq (eval a r) (eval b r) |
	Prod(a, b) -> prod (eval a r) (eval b r) |
	Sum(a, b) -> sum (eval a r) (eval b r) |
	Diff(a, b) -> diff (eval a r) (eval b r) |
	Minus a -> minus (eval a r) |
	And(a, b) -> et (eval a r) (eval b r) |
	Or(a, b) -> vel (eval a r) (eval b r) |
	Not a -> non (eval a r) |
	(*Dictionary, composto da una lista di coppie*) 
	Dictionary( (ls : 'a list ) ) -> 
		(
			match ls with 
				[] -> Dict([]) |
				(x,y) :: xs -> 
					(
						match eval (Dictionary(xs)) r with
							Dict(lis) -> 
								if not(is_in x lis) 
									then 
										Dict((x , eval y r ) :: lis )
								else 
										Dict(lis) |
							_ -> failwith("non dictionary value")
					)
		) |
	(*DictGet, restituisce il dato associato al nome passato per parametro
	avvalendosi della search*) 
	DictGet(name , v ) -> 
		let ls = applyenv r name in 
			(
				match ls with 
					Dict(lis) -> search v lis |
					_ -> failwith("non dictionary value") 
			)	|
	(*DictRm, elimina il dato associato al nome passato per parametro
	avvalendosi della delete*) 
	DictRm(name , v ) -> 
		let ls = applyenv r name in 
			(
				match ls with 
					Dict(lis) -> Dict(delete v lis) |
					_ -> failwith("non dictionary value") 
			)	|
	(*DictAdd, aggiunge il valore passato per parametro
	avvalendosi is_in per verificarne l'eventuale presenza*) 
	DictAdd(dict,value) -> 
		let ls = applyenv r dict in 
			(
				match ls with 
					Dict(list) ->
						(
							match value with
								(x,y) -> 
								if not(is_in x list) then 
										Dict((x, (eval y r))::list) 
								else 
								(
									let list2 = delete x list in 
										Dict((x, (eval y r))::list2) 
								)
						)	|
					_ ->  failwith("non dictionary value")
			) |
	(*DictClear, azzera il dizionario associato al nome passato per parametro*) 
	DictClear(name) -> 
		let ls = applyenv r name in
	 		if ls = Unbound then
				 failwith("non dictionary value")
			else 
			(
				match ls with 
					Dict(lis) -> Dict([]) |
					_ -> failwith("non dictionary value")
			) |
	(*ApplyOver, esegue una funzione passata per parametro a tutti gli elementi 
		di un dizionario (solo al secondo elemento della coppia, che rappresenta il dato)*) 
	ApplyOver(func ,dict) -> (
		match dict with
			Den x ->  
				let ls = applyenv r x in 
					(
						match ls with 
							Dict(lis) -> Dict( List.map (funeval func r) lis )	|
							_ -> failwith("non dictionary value")
					) |
			Dictionary(ls) -> 
				(
					match ls with 
						[] -> Dict([]) |
						(x,y) :: xs -> 
							(
								match eval (ApplyOver(func,Dictionary xs)) r with
									Dict(lis) -> 
										if not(is_in x lis ) 
											then 
												Dict((x, (eval (FunCall(func,y)) r) ) :: lis)
											else
												Dict(lis)	|
									_ -> failwith("non dictionary value")
							)
				) |
			_ -> failwith("non dictionary value") 
	) |
	Ifthenelse(a, b, c) -> 
		let g = (eval a r) in
			if (typecheck "bool" g) 
				then (if g = Bool(true) then (eval b r) else (eval c r))
				else failwith ("nonboolean guard") |
	Let(i, e1, e2) -> eval e2 (bind r i (eval e1 r)) |
	Fun(i, a) -> FunVal(i, a, r) |
	FunCall(f, eArg) -> 
		let fClosure = (eval f r) in
			(match fClosure with
				FunVal(arg, fBody, fDecEnv) -> 
					eval fBody (bind fDecEnv arg (eval eArg r)) |
				RecFunVal(g, (arg, fBody, fDecEnv)) -> 
					let aVal = (eval eArg r) in
						let rEnv = (bind fDecEnv g fClosure) in
							let aEnv = (bind rEnv arg aVal) in
								eval fBody aEnv |
				_ -> failwith("non functional value")) |
  Letrec(f, funDef, letBody) ->
         (match funDef with
            Fun(i, fBody) -> let r1 = (bind r f (RecFunVal(f, (i, fBody, r)))) in
                         			          eval letBody r1 |
						_ -> failwith("non functional def"))) 
	and funcalleval f r eArg =
		(let fClosure = (eval f r) in
			(match fClosure with
				FunVal(arg, fBody, fDecEnv) -> 
					eval fBody (bind fDecEnv arg eArg) |
				RecFunVal(g, (arg, fBody, fDecEnv)) -> 
					let aVal = eArg in
						let rEnv = (bind fDecEnv g fClosure) in
							let aEnv = (bind rEnv arg aVal) in
								eval fBody aEnv |
				_ -> failwith("non functional value")))
	and funeval f r v  = 
		match v with 
			(x,y) -> ( x , funcalleval f r (y)) 
;;

(* ==========================================================  BATTERIA DI TEST ========================================================== *)
let env0 = emptyenv Unbound;;
let diz1 = Dictionary([]);;
eval diz1 env0;;
let diz2 = Let("testerdiz", diz1 , DictAdd("testerdiz",("Utente1",Eint(2))));;
eval diz2 env0;;
let diz2 = Let("testerdiz", diz1 , DictRm("testerdiz","Utente1"));;
eval diz2 env0;;
let diz2 = Let("testerdiz", diz1 , Let("testerdiz2",DictClear("testerdiz"),Den "testerdiz2"));;
eval diz2 env0;;
let diz2 = Let("testerdiz", diz1 , Let("testerdiz2",DictAdd( "testerdiz", ("Utente2", Eint 8 ) ),Den "testerdiz2"));;
eval diz2 env0;;
let diz2 = Let("testerdiz", diz1 , ApplyOver(Fun("y", Sum(Den "y", Eint 1)), diz1 ));;
eval diz2 env0;;
let diz2 = Let("test", diz1 , ApplyOver(Fun("y", Sum(Den "y", Eint 1)), Den "test"));;
eval diz2 env0;;
let diz2 = Dictionary([("Utente3",Eint 3) ; ("Utente1" , Eint 5) ; ("Utente4" , Eint 10)]);;
eval diz2 env0;;
let diz3 = Let("testerdiz", diz2 , DictGet("testerdiz","Utente1"));;
eval diz3 env0;;
let diz4= Let("testerdiz", diz2 , DictRm("testerdiz","Utente4"));;
eval diz4 env0;;
let diz5= Let("testerdiz", diz2 , Let("testerdiz2",DictClear("testerdiz"),Den "testerdiz2"));;
eval diz5 env0;;
let diz6 = Let("testerdiz", diz2 , Let("testerdiz2",DictAdd( "testerdiz", ("Utente2", Eint 8 ) ),Den "testerdiz2"));;
eval diz6 env0;;
let diz7 = Let("testerdiz", diz2 , ApplyOver(Fun("y", Sum(Den "y", Eint 1)), diz1 ));;
eval diz7 env0;;
let diz8 = Let("test", diz2 , ApplyOver(Fun("y", Sum(Den "y", Eint 1)), Den "test"));;
eval diz8 env0;;



