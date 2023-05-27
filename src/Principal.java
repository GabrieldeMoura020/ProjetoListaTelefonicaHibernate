import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Normalizer.Form;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import br.com.empresa.dao.HibernateUtilLT;
import br.com.empresa.vo.ContatoVO;
import br.com.empresa.vo.FormaContatoVO;

public class Principal {

	public static void main(String[] args) throws ParseException {

		Principal p = new Principal();
		// p.inserirContato();
		// p.editarContato();
		// p.excluirProduto();
		// p.consultarProdutoSimplesCount();
		p.consultarProdutoComBetWeen();

		System.exit(0);

	}

	private void consultarProdutoComBetWeen() {

		// CONSULTAR PRODUTO COM MAIOR IGUAL
		EntityManager em = HibernateUtilLT.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<FormaContatoVO> criteria = cb.createQuery(FormaContatoVO.class);

		// CLÁUSULA FROM
		Root<FormaContatoVO> fcFrom = criteria.from(FormaContatoVO.class);

		// CLÁUSULA WHERE
		ContatoVO c1 = new ContatoVO();
		c1.setId(BigInteger.ONE);

		Predicate fcWhere = cb.equal(fcFrom.get("contat"), c1);

		fcWhere = cb.and(fcWhere, cb.between(fcFrom.get("id"), 1, 5));

		fcWhere = cb.and(fcWhere, cb.greaterThanOrEqualTo(fcFrom.get("id"), 10));

		fcWhere = cb.and(fcWhere, cb.lessThanOrEqualTo(fcFrom.get("id"), 50));

		// ORDER BY
		Order fcOrderBY = cb.asc(fcFrom.get("tipcon"));

		// ATRIBUINDO AS CLÁUSULAS AS CONSULTAS
		criteria.select(fcFrom);
		criteria.where(fcWhere);
		criteria.orderBy(fcOrderBY);

		TypedQuery<FormaContatoVO> query = em.createQuery(criteria);

		List<FormaContatoVO> listaContato = query.getResultList();

		for (FormaContatoVO fcVO : listaContato) {
			System.out.println("Produto: " + fcVO.getId() + " - " + fcVO.getTipcon());

		}

		em.close();

	}

	private void consultarProdutoSimplesCount() {

		EntityManager em = HibernateUtilLT.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = cb.createQuery(Long.class);

		// CLÁUSULA FROM
		Root<FormaContatoVO> produtoFrom = criteria.from(FormaContatoVO.class);

		// CLÁUSULA SELECT
		Selection<Long> produtoSelect = cb.count(produtoFrom);

		criteria.select(produtoSelect);

		TypedQuery<Long> query = em.createQuery(criteria);

		Long ret = (Long) query.getSingleResult();
		em.close();

		System.out.println("Quantidade no banco: " + ret);

	}

	private void excluirProduto() {

		System.out.println("Excluindo Produto!");

		EntityManager em = HibernateUtilLT.getEntityManager();

		try {

			em.getTransaction().begin();

			FormaContatoVO fc = em.find(FormaContatoVO.class, new BigInteger("47"));
			ContatoVO c = em.find(ContatoVO.class, new BigInteger("47"));

			em.remove(fc);
			em.remove(c);
			em.getTransaction().commit();

		} catch (Exception e) {

			e.printStackTrace();
			em.getTransaction().rollback();

		} finally {

			em.close();

		}

	}

	private void editarContato() {

		System.out.println("Editando contato!");

		EntityManager em = HibernateUtilLT.getEntityManager();

		try {

			em.getTransaction().begin();

			ContatoVO c = em.find(ContatoVO.class, new BigInteger("40"));
			c.setNomcon("ANA PAULA BORGES GARCIA DE MOURA");

			em.merge(c);
			em.getTransaction().commit();

		} catch (Exception e) {

			e.printStackTrace();
			em.getTransaction().rollback();

		} finally {

			em.close();

		}

	}

	private void inserirContato() throws ParseException {

		System.out.println("Inserindo contato!");

		EntityManager em = HibernateUtilLT.getEntityManager();

		FormaContatoVO fc = new FormaContatoVO();
		fc.setTipcon("E");
		fc.setEmacon("nicole.moura@gmail.com");
		// fc.setId(BigInteger.ONE);

		ContatoVO c = new ContatoVO();

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date data = formato.parse("08/02/2010");

		c.setNomcon("NICOLE ROCHADEL DE MOURA");
		c.setDatnas(data);
		c.setEndere("AVENIDA METROPOLITANA 471, CIDADE MINEIRA NOVA");
		c.setObserv("");

		try {

			em.getTransaction().begin();
			em.persist(c);
			fc.setContat(c);
			em.persist(fc);
			em.getTransaction().commit();

		} catch (Exception e) {

			e.printStackTrace();
			em.getTransaction().rollback();

		}

	}
}
