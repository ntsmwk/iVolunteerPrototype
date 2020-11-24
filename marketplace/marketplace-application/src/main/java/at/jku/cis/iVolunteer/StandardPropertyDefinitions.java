package at.jku.cis.iVolunteer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.flatProperty.FlatPropertyDefinitionRepository;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinitionTypes.BooleanPropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinitionTypes.DatePropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinitionTypes.DoublePropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinitionTypes.LocationPropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinitionTypes.LongPropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinitionTypes.LongTextPropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinitionTypes.TextPropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty.TreePropertyEntry;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty.TreePropertyRelationship;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Component
public class StandardPropertyDefinitions {

	@Autowired
	public FlatPropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired
	public CoreTenantRestClient coreTenantRestClient;

	private List<Tenant> getTenants() {
		return coreTenantRestClient.getAllTenants();
	}

	public List<FlatPropertyDefinition<Object>> getAlliVolunteer(String tenantId) {
		List<FlatPropertyDefinition<Object>> properties = getAllHeader(tenantId);
		properties.addAll(getAllGeneric(tenantId));
		properties.add(new FlatPropertyDefinition<Object>("IVolunteerUUID", PropertyType.TEXT, tenantId));
		properties.add(new FlatPropertyDefinition<Object>("IVolunteerSource", PropertyType.TEXT, tenantId));
		properties.add(new FlatPropertyDefinition<Object>("IssuedOn", PropertyType.DATE, tenantId));
		properties.add(new FlatPropertyDefinition<Object>("Icon", PropertyType.TEXT, tenantId));
		properties.add(new FlatPropertyDefinition<Object>("Purpose", PropertyType.TEXT, tenantId));
		properties.add(new FlatPropertyDefinition<Object>("Phase", PropertyType.TEXT, tenantId));
		properties.add(new FlatPropertyDefinition<Object>("Unit", PropertyType.TEXT, tenantId));
		properties.add(new FlatPropertyDefinition<Object>("Level", PropertyType.TEXT, tenantId));
		properties.add(new FlatPropertyDefinition<Object>("GeoInformation", PropertyType.TEXT, tenantId));
		properties.add(new FlatPropertyDefinition<Object>("RoleID", PropertyType.TEXT, tenantId));
		properties.add(new FlatPropertyDefinition<Object>("OrganisationID", PropertyType.TEXT, tenantId));
		properties.add(new FlatPropertyDefinition<Object>("OrganisationName", PropertyType.TEXT, tenantId));
		properties.add(new FlatPropertyDefinition<Object>("OrganisationType", PropertyType.TEXT, tenantId));
		properties.add(new FlatPropertyDefinition<Object>("BadgeID", PropertyType.TEXT, tenantId));
		properties.add(new FlatPropertyDefinition<Object>("CertificateID", PropertyType.TEXT, tenantId));
		properties.add(new FlatPropertyDefinition<Object>("TaskID", PropertyType.TEXT, tenantId));

		return properties;
	}

	public List<FlatPropertyDefinition<Object>> getAllHeader(String tenantId) {
		List<FlatPropertyDefinition<?>> props = new LinkedList<>();

		props.add(new IDProperty(tenantId));
		props.add(new EvidenzProperty(tenantId));
		props.add(new DateFromProperty(tenantId));
		props.add(new DateToProperty(tenantId));
		props.add(new ImageLinkProperty(tenantId));
		props.add(new ExpiredProperty(tenantId));
		props.add(new RankProperty(tenantId));
		props.add(new DurationProperty(tenantId));

		return new ArrayList(props);
	}

	public List<FlatPropertyDefinition<Object>> getAllTest(String tenantId) {
		List<FlatPropertyDefinition<?>> props = new LinkedList<>();

		props.add(new TestTextProperty(tenantId));
		props.add(new TestLongTextProperty(tenantId));
		props.add(new TestWholeNumberProperty(tenantId));
		props.add(new TestFloatNumberProperty(tenantId));
		props.add(new TestBooleanProperty(tenantId));
		props.add(new TestDateProperty(tenantId));

		return new ArrayList(props);
	}

	public List<TreePropertyDefinition> getAllTreeProperties(String tenantId) {
		List<TreePropertyDefinition> props = new ArrayList<>();
		List<Tenant> tenants = getTenants();

		if (tenants.stream().filter(t -> t.getName().equals("FF Eidenberg")).map(t -> t.getId())
				.collect(Collectors.toList()).contains(tenantId)) {
			props.add(new TaskTypePropertyFF(tenantId));
		}
		if (tenants.stream().filter(t -> t.getName().equals("MV Schwertberg")).map(t -> t.getId())
				.collect(Collectors.toList()).contains(tenantId)) {
			props.add(new TaskTypePropertyMV(tenantId));
		}

		return props;
	}

	public List<FlatPropertyDefinition<Object>> getAllGeneric(String tenantId) {
		List<FlatPropertyDefinition<?>> props = new LinkedList<>();

		props.add(new NameProperty(tenantId));
		props.add(new DescriptionProperty(tenantId));
		props.add(new WorkflowKeyProperty(tenantId));
		props.add(new ContentProperty(tenantId));
		props.add(new PriorityProperty(tenantId));
		props.add(new ImportancyProperty(tenantId));
		props.add(new RoleProperty(tenantId));
		props.add(new LocationProperty(tenantId));
		props.add(new RequiredEquipmentProperty(tenantId));
		props.add(new WorkshiftProperty(tenantId));
		props.add(new TaskPeriodTypeProperty(tenantId));
		props.add(new KeywordsProperty(tenantId));
		props.add(new RewardsProperty(tenantId));
		props.add(new PostcodeProperty(tenantId));
		props.add(new NumberOfVolunteersProperty(tenantId));
		props.add(new TaskPeriodValueProperty(tenantId));
		props.add(new StartDateProperty(tenantId));
		props.add(new EndDateProperty(tenantId));
		props.add(new UrgentProperty(tenantId));
		props.add(new HighlightedProperty(tenantId));
		props.add(new PromotedProperty(tenantId));
		props.add(new FeedbackRequestedProperty(tenantId));
		props.add(new RemindParticipantsProperty(tenantId));
		props.add(new LatitudeProperty(tenantId));
		props.add(new LongitudeProperty(tenantId));
		props.add(new VolunteerAgeProperty(tenantId));
		props.add(new BereichProperty(tenantId));

		return new ArrayList(props);
	}

	public List<FlatPropertyDefinition<Object>> getAllFlexProdProperties(String tenantId) {
		List<FlatPropertyDefinition<?>> list = new LinkedList<>();

		list.add(new MaxGluehtemperaturProperty(tenantId));
		list.add(new VerfuegbaresSchutzgasProperty(tenantId));
		list.add(new BauartProperty(tenantId));
		list.add(new TemperaturhomogenitaetProperty(tenantId));
		list.add(new KaltgewalztesMaterialZulaessigProperty(tenantId));
		list.add(new WarmgewalztesMaterialZulaessigProperty(tenantId));

		list.add(new BundEntfettenProperty(tenantId));

		list.add(new InnendurchmesserProperty(tenantId));
		list.add(new AussendurchmesserProperty(tenantId));
		list.add(new HoeheProperty(tenantId));

		list.add(new GluehzeitProperty(tenantId));
		list.add(new DurchmesserProperty(tenantId));
		list.add(new DurchsatzProperty(tenantId));

		list.add(new ChargierhilfeProperty(tenantId));
		list.add(new WalzartProperty(tenantId));

		list.add(new MoeglicheInnendurchmesserProperty(tenantId));
		list.add(new MaxAussendurchmesserProperty(tenantId));
		list.add(new MaxChargierhoeheProperty(tenantId));

		list.add(new CQI9Property(tenantId));
		list.add(new TUSProperty(tenantId));

		list.add(new LetzteWartungProperty(tenantId));
		list.add(new WartungsintervallProperty(tenantId));

		list.add(new BandBreiteProperty(tenantId));
		list.add(new BandstaerkeProperty(tenantId));

		list.add(new WarmgewalztProperty(tenantId));
		list.add(new KaltgewalztProperty(tenantId));

		list.add(new StreckgrenzeProperty(tenantId));
		list.add(new ZugfestigkeitProperty(tenantId));
		list.add(new DehnungProperty(tenantId));

		list.add(new GefuegeProperty(tenantId));

		list.add(new MaterialBereitgestelltProperty(tenantId));
		list.add(new LieferortProperty(tenantId));
		list.add(new VerpackungProperty(tenantId));
		list.add(new TransportartProperty(tenantId));
		list.add(new MengeProperty(tenantId));
		list.add(new LieferdatumProperty(tenantId));
		list.add(new IncotermsProperty(tenantId));

		list.add(new ZahlungsbedingungenProperty(tenantId));

		list.add(new TitelProperty(tenantId));
		list.add(new ProdukttypProperty(tenantId));
		list.add(new MengeProperty(tenantId));
		list.add(new MinimaleMengeProperty(tenantId));
		list.add(new LieferdatumProperty(tenantId));
		list.add(new WerkstoffBereitgestelltProperty(tenantId));
		list.add(new BeschreibungZusatzinfoProperty(tenantId));

		list.add(new DurchmesserInnenProperty(tenantId));
		list.add(new DurchmesserAussenProperty(tenantId));
		list.add(new HoeheProperty(tenantId));

		list.add(new WerkstoffProperty(tenantId));
		list.add(new WerkstoffFreitextProperty(tenantId));
		list.add(new ZugfestigkeitProperty(tenantId));

		list.add(new SchutzgasProperty(tenantId));
		list.add(new GluehreiseProperty(tenantId));
		list.add(new TemperaturhomogenitaetProperty(tenantId));

		list.add(new OberflaechenqualitaetProperty(tenantId));
		list.add(new ZusaetzlicheProduktinformationenProperty(tenantId));

		list.add(new IncotermsProperty(tenantId));
		list.add(new LieferortProperty(tenantId));
		list.add(new AbholortProperty(tenantId));
		list.add(new VerpackungsvorgabenProperty(tenantId));

		list.add(new BandDickeProperty(tenantId));

		list.add(new DurchmesserKronenstockProperty(tenantId));
		list.add(new MaximaldurchmesserBundProperty(tenantId));

		list.add(new DurchmesserDornProperty(tenantId));
		list.add(new InnendurchmesserOfenProperty(tenantId));

		list.add(new OfenHoeheProperty(tenantId));
		list.add(new MaxGluehtemperaturProperty(tenantId));
		list.add(new TemperaturhomogenitaetProperty(tenantId));
		list.add(new ErforderlicheTemperaturhomogenitaetProperty(tenantId));
		list.add(new AufheizrateProperty(tenantId));
		list.add(new AbkuehlrateProperty(tenantId));
		list.add(new MaxAnteilH2Property(tenantId));
		list.add(new KapazitaetProperty(tenantId));
		list.add(new GluehprogrammVerfuegbarProperty(tenantId));

		return new ArrayList(list);
	}

	/**
	 * 
	 * Header Properties
	 *
	 */
	public static class IDProperty extends TextPropertyDefinition {

		IDProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("ID");
			this.setTenantId(tenantId);
		}
	}

	public static class EvidenzProperty extends TextPropertyDefinition {

		EvidenzProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("Evidenz");
			this.setTenantId(tenantId);
		}
	}

	public static class ImageLinkProperty extends TextPropertyDefinition {

		ImageLinkProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("Image Link");
			this.setTenantId(tenantId);
		}
	}

	public static class ExpiredProperty extends BooleanPropertyDefinition {

		ExpiredProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("Expired");
			this.setTenantId(tenantId);
		}
	}

	public static class LocationProperty extends LocationPropertyDefinition {

		LocationProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("Location");
			this.setTenantId(tenantId);
		}
	}

	public static class DateFromProperty extends DatePropertyDefinition {

		DateFromProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("Starting Date");
			this.setTenantId(tenantId);
		}
	}

	public static class DateToProperty extends DatePropertyDefinition {

		DateToProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("End Date");
			this.setTenantId(tenantId);
		}
	}

	public static class BereichProperty extends TextPropertyDefinition {

		BereichProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("Bereich");
			this.setTenantId(tenantId);
			this.setAllowedValues(Arrays.asList("Katastrophenhilfs- & Rettungsdienste", "Ältere Menschen", "Soziales",
					"Entwicklungshilfe, Frieden, Menschrechte", "Gesundheit & Pflege", "Kirche & Religion",
					"Bürgerschaftliche Aktivitäten & Gemeinwesen", "Sport & Bewegung", "Kinder & Jugendliche",
					"Bildung & Coaching", "Natur, Umwelt, Tierschutz", "Kultur, Kunst, Unterhaltung, Freizeit",
					"Menschen mit Beeinträchtigung", "Migrantinnen und Migranten"));
		}
	}

	public static class TaskTypePropertyMV extends TreePropertyDefinition {

		TaskTypePropertyMV(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("TaskType");
			this.setTenantId(tenantId);
			this.setRequired(true);
			this.setId(UUID.randomUUID().toString());

			// Ausrückung
			TreePropertyEntry a = new TreePropertyEntry("Ausrückung", false, 0, true);
			TreePropertyEntry a1 = new TreePropertyEntry("Bläsergruss", false, 1, true);
			TreePropertyEntry a2 = new TreePropertyEntry("Kirche", false, 1, true);
			TreePropertyEntry a3 = new TreePropertyEntry("Musikalische Gestaltung", false, 1, true);
			TreePropertyEntry a4 = new TreePropertyEntry("Allgemein", true, 1, true);
			TreePropertyEntry a5 = new TreePropertyEntry("Konzert", false, 1, true);
			TreePropertyEntry a6 = new TreePropertyEntry("Wertung", false, 1, true);
			TreePropertyEntry a1_1 = new TreePropertyEntry("Ganztägig Bläsergruß", true, 2, true);
			TreePropertyEntry a2_1 = new TreePropertyEntry("Turmblasen (Gruppe)", true, 2, true);
			TreePropertyEntry a2_2 = new TreePropertyEntry("Messe und Frühschoppen Geyer", true, 2, true);
			TreePropertyEntry a2_3 = new TreePropertyEntry("Erstkommunion", true, 2, true);
			TreePropertyEntry a2_4 = new TreePropertyEntry("Florianimesse", true, 2, true);
			TreePropertyEntry a2_5 = new TreePropertyEntry("Palmweihe (Gruppe)", true, 2, true);
			TreePropertyEntry a3_1 = new TreePropertyEntry("Bläserquartett Weihnachtsmarkt", true, 2, true);
			TreePropertyEntry a3_2 = new TreePropertyEntry("Eröffnung Weihnachtsmarkt", true, 2, true);
			TreePropertyEntry a5_1 = new TreePropertyEntry("Konzert im Schloß", true, 2, true);
			TreePropertyEntry a6_1 = new TreePropertyEntry("Konzertwertung", true, 2, true);

			TreePropertyRelationship relationshipA1 = new TreePropertyRelationship(a, a1, true);
			TreePropertyRelationship relationshipA2 = new TreePropertyRelationship(a, a2, true);
			TreePropertyRelationship relationshipA3 = new TreePropertyRelationship(a, a3, true);
			TreePropertyRelationship relationshipA4 = new TreePropertyRelationship(a, a4, true);
			TreePropertyRelationship relationshipA5 = new TreePropertyRelationship(a, a5, true);
			TreePropertyRelationship relationshipA6 = new TreePropertyRelationship(a, a6, true);
			TreePropertyRelationship relationshipA7 = new TreePropertyRelationship(a1, a1_1, true);
			TreePropertyRelationship relationshipA8 = new TreePropertyRelationship(a2, a2_1, true);
			TreePropertyRelationship relationshipA9 = new TreePropertyRelationship(a2, a2_2, true);
			TreePropertyRelationship relationshipA10 = new TreePropertyRelationship(a2, a2_3, true);
			TreePropertyRelationship relationshipA11 = new TreePropertyRelationship(a2, a2_4, true);
			TreePropertyRelationship relationshipA12 = new TreePropertyRelationship(a2, a2_5, true);
			TreePropertyRelationship relationshipA13 = new TreePropertyRelationship(a3, a3_1, true);
			TreePropertyRelationship relationshipA14 = new TreePropertyRelationship(a3, a3_2, true);
			TreePropertyRelationship relationshipA15 = new TreePropertyRelationship(a5, a5_1, true);
			TreePropertyRelationship relationshipA16 = new TreePropertyRelationship(a6, a6_1, true);

			this.setEntries(Stream.concat(this.getEntries().stream(),
					Arrays.asList(a, a1, a2, a3, a4, a5, a6, a1_1, a2_1, a2_2, a2_3, a2_4, a2_5, a3_1, a3_2, a5_1, a6_1)
							.stream())
					.collect(Collectors.toList()));
			this.setRelationships(Stream.concat(this.getRelationships().stream(),
					Arrays.asList(relationshipA1, relationshipA2, relationshipA3, relationshipA4, relationshipA5,
							relationshipA6, relationshipA7, relationshipA8, relationshipA9, relationshipA10,
							relationshipA11, relationshipA12, relationshipA13, relationshipA14, relationshipA15,
							relationshipA16).stream())
					.collect(Collectors.toList()));

			// Probe
			TreePropertyEntry p = new TreePropertyEntry("Probe", false, 0, true);
			TreePropertyEntry p1 = new TreePropertyEntry("Jugendorchesterprobe", false, 1, true);
			TreePropertyEntry p2 = new TreePropertyEntry("Musikprobe", false, 1, true);
			TreePropertyEntry p3 = new TreePropertyEntry("Marschprobe", false, 1, true);
			TreePropertyEntry p4 = new TreePropertyEntry("Registerprobe", false, 1, true);
			TreePropertyEntry p1_1 = new TreePropertyEntry("Allgemein", true, 2, true);
			TreePropertyEntry p1_2 = new TreePropertyEntry("Weihnachtsmarkt", true, 2, true);
			TreePropertyEntry p2_1 = new TreePropertyEntry("Kekserlprobe", true, 2, true);
			TreePropertyEntry p2_2 = new TreePropertyEntry("Lehre", true, 2, true);
			TreePropertyEntry p2_3 = new TreePropertyEntry("Allgemein", true, 2, true);
			TreePropertyEntry p2_4 = new TreePropertyEntry("Ständchen", true, 2, true);
			TreePropertyEntry p3_1 = new TreePropertyEntry("Allgemein", true, 2, true);
			TreePropertyEntry p4_1 = new TreePropertyEntry("Horn Sax", true, 2, true);
			TreePropertyEntry p4_2 = new TreePropertyEntry("Trompete Horn", true, 2, true);
			TreePropertyEntry p4_3 = new TreePropertyEntry("Tuba Tenor Bariton", true, 2, true);

			TreePropertyRelationship relationshipP1 = new TreePropertyRelationship(p, p1, true);
			TreePropertyRelationship relationshipP2 = new TreePropertyRelationship(p, p2, true);
			TreePropertyRelationship relationshipP3 = new TreePropertyRelationship(p, p3, true);
			TreePropertyRelationship relationshipP4 = new TreePropertyRelationship(p, p4, true);
			TreePropertyRelationship relationshipP5 = new TreePropertyRelationship(p1, p1_1, true);
			TreePropertyRelationship relationshipP6 = new TreePropertyRelationship(p1, p1_2, true);
			TreePropertyRelationship relationshipP7 = new TreePropertyRelationship(p2, p2_1, true);
			TreePropertyRelationship relationshipP8 = new TreePropertyRelationship(p2, p2_2, true);
			TreePropertyRelationship relationshipP9 = new TreePropertyRelationship(p2, p2_3, true);
			TreePropertyRelationship relationshipP10 = new TreePropertyRelationship(p2, p2_4, true);
			TreePropertyRelationship relationshipP11 = new TreePropertyRelationship(p3, p3_1, true);
			TreePropertyRelationship relationshipP12 = new TreePropertyRelationship(p4, p4_1, true);
			TreePropertyRelationship relationshipP13 = new TreePropertyRelationship(p4, p4_2, true);
			TreePropertyRelationship relationshipP14 = new TreePropertyRelationship(p4, p4_3, true);

			this.setEntries(Stream.concat(this.getEntries().stream(), Arrays
					.asList(p, p1, p2, p3, p4, p1_1, p1_2, p2_1, p2_2, p2_3, p2_4, p3_1, p4_1, p4_2, p4_3).stream())
					.collect(Collectors.toList()));
			this.setRelationships(Stream.concat(this.getRelationships().stream(),
					Arrays.asList(relationshipP1, relationshipP2, relationshipP3, relationshipP4, relationshipP5,
							relationshipP6, relationshipP7, relationshipP8, relationshipP9, relationshipP10,
							relationshipP11, relationshipP12, relationshipP13, relationshipP14).stream())
					.collect(Collectors.toList()));

			// Tätigkeit
			TreePropertyEntry t = new TreePropertyEntry("Tätigkeit", false, 0, true);
			TreePropertyEntry t1 = new TreePropertyEntry("Musikheim", false, 1, true);
			TreePropertyEntry t1_1 = new TreePropertyEntry("Musikheim", true, 2, true);
			TreePropertyRelationship relationshipT1 = new TreePropertyRelationship(t, t1, true);
			TreePropertyRelationship relationshipT2 = new TreePropertyRelationship(t1, t1_1, true);

			this.setEntries(Stream.concat(this.getEntries().stream(), Arrays.asList(t, t1, t1_1).stream())
					.collect(Collectors.toList()));
			this.setRelationships(Stream
					.concat(this.getRelationships().stream(), Arrays.asList(relationshipT1, relationshipT2).stream())
					.collect(Collectors.toList()));

			// Veranstaltung
			TreePropertyEntry v = new TreePropertyEntry("Veranstaltung", false, 0, true);
			TreePropertyEntry v1 = new TreePropertyEntry("Vorstand", false, 1, true);
			TreePropertyEntry v2 = new TreePropertyEntry("Allgemein", false, 1, true);
			TreePropertyEntry v1_1 = new TreePropertyEntry("Vorstandssitzung", true, 2, true);
			TreePropertyEntry v1_2 = new TreePropertyEntry("Erweiterte Bezirkssitzung", true, 2, true);
			TreePropertyEntry v2_1 = new TreePropertyEntry("Diskussion Konzertprogramm", true, 2, true);
			TreePropertyEntry v2_2 = new TreePropertyEntry("Jause und Noten sortieren", true, 2, true);
			TreePropertyEntry v2_3 = new TreePropertyEntry("Spatenstich Rückhaltebecken (Quartett)", true, 2, true);
			TreePropertyEntry v2_4 = new TreePropertyEntry("Jahreshauptversammlung", true, 2, true);

			TreePropertyRelationship relationshipV1 = new TreePropertyRelationship(v, v1, true);
			TreePropertyRelationship relationshipV2 = new TreePropertyRelationship(v, v2, true);
			TreePropertyRelationship relationshipV3 = new TreePropertyRelationship(v1, v1_1, true);
			TreePropertyRelationship relationshipV4 = new TreePropertyRelationship(v1, v1_2, true);
			TreePropertyRelationship relationshipV5 = new TreePropertyRelationship(v2, v2_1, true);
			TreePropertyRelationship relationshipV6 = new TreePropertyRelationship(v2, v2_2, true);
			TreePropertyRelationship relationshipV7 = new TreePropertyRelationship(v2, v2_3, true);
			TreePropertyRelationship relationshipV8 = new TreePropertyRelationship(v2, v2_4, true);

			this.setEntries(Stream
					.concat(this.getEntries().stream(),
							Arrays.asList(v, v1, v2, v1_1, v1_2, v2_1, v2_2, v2_3, v2_4).stream())
					.collect(Collectors.toList()));
			this.setRelationships(Stream
					.concat(this.getRelationships().stream(),
							Arrays.asList(relationshipV1, relationshipV2, relationshipV3, relationshipV4,
									relationshipV5, relationshipV6, relationshipV7, relationshipV8).stream())
					.collect(Collectors.toList()));

			TreePropertyRelationship rel1 = new TreePropertyRelationship(this.getId(), a.getId(), true);
			TreePropertyRelationship rel2 = new TreePropertyRelationship(this.getId(), p.getId(), true);
			TreePropertyRelationship rel3 = new TreePropertyRelationship(this.getId(), t.getId(), true);
			TreePropertyRelationship rel4 = new TreePropertyRelationship(this.getId(), v.getId(), true);
			this.setRelationships(
					Stream.concat(this.getRelationships().stream(), Arrays.asList(rel1, rel2, rel3, rel4).stream())
							.collect(Collectors.toList()));
		}
	}

	public static class TaskTypePropertyFF extends TreePropertyDefinition {

		TaskTypePropertyFF(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("TaskType");
			this.setRequired(true);
			this.setTenantId(tenantId);
			this.setId(UUID.randomUUID().toString());

			// Einsatz
			TreePropertyEntry einsatz = new TreePropertyEntry("Einsatz", false, 0, true);
			TreePropertyEntry technischerEinsatz = new TreePropertyEntry("Technischer Einsatz", false, 1, true);
			TreePropertyEntry brandeinsatz = new TreePropertyEntry("Brandeinsatz", false, 1, true);
			TreePropertyEntry t1 = new TreePropertyEntry("T1", true, 2, true);
			TreePropertyEntry tbd = new TreePropertyEntry("TBD", true, 2, true);
			TreePropertyEntry tbd2 = new TreePropertyEntry("TBD", true, 2, true);
			TreePropertyRelationship relationshipEinsatz1 = new TreePropertyRelationship(einsatz, technischerEinsatz,
					true);
			TreePropertyRelationship relationshipEinsatz2 = new TreePropertyRelationship(einsatz, brandeinsatz, true);
			TreePropertyRelationship relationshipEinsatz3 = new TreePropertyRelationship(technischerEinsatz, t1, true);
			TreePropertyRelationship relationshipEinsatz4 = new TreePropertyRelationship(technischerEinsatz, tbd, true);
			TreePropertyRelationship relationshipEinsatz5 = new TreePropertyRelationship(brandeinsatz, tbd2, true);
			this.setEntries(Stream
					.concat(this.getEntries().stream(),
							Arrays.asList(einsatz, technischerEinsatz, t1, tbd, brandeinsatz, tbd2).stream())
					.collect(Collectors.toList()));
			this.setRelationships(Stream
					.concat(this.getRelationships().stream(), Arrays.asList(relationshipEinsatz1, relationshipEinsatz2,
							relationshipEinsatz3, relationshipEinsatz4, relationshipEinsatz5).stream())
					.collect(Collectors.toList()));

			// Bewerb
			TreePropertyEntry bewerb = new TreePropertyEntry("Bewerb", false, 0, true);
			TreePropertyEntry teilnahme = new TreePropertyEntry("Teilnahme", false, 1, true);
			TreePropertyEntry vorbereitung = new TreePropertyEntry("Vorbereitung", false, 1, true);
			TreePropertyEntry pruefung = new TreePropertyEntry("Prüfung", false, 1, true);
			TreePropertyEntry fla = new TreePropertyEntry("FLA", true, 2, true);
			TreePropertyEntry thl = new TreePropertyEntry("THL", true, 2, true);
			TreePropertyEntry fla2 = new TreePropertyEntry("FLA", true, 2, true);
			TreePropertyEntry sonstiges = new TreePropertyEntry("Sonstiges", true, 2, true);
			TreePropertyEntry thl2 = new TreePropertyEntry("THL", true, 2, true);
			TreePropertyEntry thl3 = new TreePropertyEntry("THL", true, 2, true);
			TreePropertyRelationship relationshipBewerb1 = new TreePropertyRelationship(bewerb, teilnahme, true);
			TreePropertyRelationship relationshipBewerb2 = new TreePropertyRelationship(bewerb, vorbereitung, true);
			TreePropertyRelationship relationshipBewerb3 = new TreePropertyRelationship(bewerb, pruefung, true);
			TreePropertyRelationship relationshipBewerb4 = new TreePropertyRelationship(vorbereitung, fla, true);
			TreePropertyRelationship relationshipBewerb5 = new TreePropertyRelationship(vorbereitung, thl, true);
			TreePropertyRelationship relationshipBewerb6 = new TreePropertyRelationship(teilnahme, fla2, true);
			TreePropertyRelationship relationshipBewerb7 = new TreePropertyRelationship(teilnahme, sonstiges, true);
			TreePropertyRelationship relationshipBewerb8 = new TreePropertyRelationship(teilnahme, thl2, true);
			TreePropertyRelationship relationshipBewerb9 = new TreePropertyRelationship(pruefung, thl3, true);
			this.setEntries(Stream.concat(this.getEntries().stream(), Arrays
					.asList(bewerb, teilnahme, vorbereitung, pruefung, fla, thl, fla2, sonstiges, thl2, thl3).stream())
					.collect(Collectors.toList()));
			this.setRelationships(Stream.concat(this.getRelationships().stream(),
					Arrays.asList(relationshipBewerb1, relationshipBewerb2, relationshipBewerb3, relationshipBewerb4,
							relationshipBewerb5, relationshipBewerb6, relationshipBewerb7, relationshipBewerb8,
							relationshipBewerb9).stream())
					.collect(Collectors.toList()));

			// Ausbildung
			TreePropertyEntry ausbildung = new TreePropertyEntry("Ausbildung (aktiv)", false, 0, true);
			TreePropertyEntry schulung = new TreePropertyEntry("Schulung", false, 1, true);
			TreePropertyEntry uebung = new TreePropertyEntry("Übung", false, 1, true);
			TreePropertyEntry bd = new TreePropertyEntry("BD", true, 2, true);
			TreePropertyEntry te = new TreePropertyEntry("TE", true, 2, true);
			TreePropertyEntry so = new TreePropertyEntry("SO", true, 2, true);
			TreePropertyEntry gk = new TreePropertyEntry("GK", true, 2, true);
			TreePropertyEntry allgemein = new TreePropertyEntry("Allgemein", true, 2, true);
			TreePropertyEntry bd2 = new TreePropertyEntry("BD", true, 2, true);
			TreePropertyEntry te2 = new TreePropertyEntry("TE", true, 2, true);
			TreePropertyEntry so2 = new TreePropertyEntry("SO", true, 2, true);
			TreePropertyEntry allgemein2 = new TreePropertyEntry("Allgemein", true, 2, true);
			TreePropertyRelationship relationshipAusbildung1 = new TreePropertyRelationship(ausbildung, schulung, true);
			TreePropertyRelationship relationshipAusbildung2 = new TreePropertyRelationship(ausbildung, uebung, true);
			TreePropertyRelationship relationshipAusbildung3 = new TreePropertyRelationship(schulung, bd, true);
			TreePropertyRelationship relationshipAusbildung4 = new TreePropertyRelationship(schulung, te, true);
			TreePropertyRelationship relationshipAusbildung5 = new TreePropertyRelationship(schulung, so, true);
			TreePropertyRelationship relationshipAusbildung6 = new TreePropertyRelationship(schulung, gk, true);
			TreePropertyRelationship relationshipAusbildung7 = new TreePropertyRelationship(schulung, allgemein, true);
			TreePropertyRelationship relationshipAusbildung8 = new TreePropertyRelationship(uebung, bd2, true);
			TreePropertyRelationship relationshipAusbildung9 = new TreePropertyRelationship(uebung, te2, true);
			TreePropertyRelationship relationshipAusbildung10 = new TreePropertyRelationship(uebung, so2, true);
			TreePropertyRelationship relationshipAusbildung11 = new TreePropertyRelationship(uebung, allgemein2, true);
			this.setEntries(Stream.concat(this.getEntries().stream(),
					Arrays.asList(ausbildung, schulung, uebung, bd, te, so, gk, allgemein, bd2, te2, so2, allgemein2)
							.stream())
					.collect(Collectors.toList()));
			this.setRelationships(Stream
					.concat(this.getRelationships().stream(),
							Arrays.asList(relationshipAusbildung1, relationshipAusbildung2, relationshipAusbildung3,
									relationshipAusbildung4, relationshipAusbildung5, relationshipAusbildung6,
									relationshipAusbildung7, relationshipAusbildung8, relationshipAusbildung9,
									relationshipAusbildung10, relationshipAusbildung11).stream())
					.collect(Collectors.toList()));

			// Jugendarbeit
			TreePropertyEntry jugendarbeit = new TreePropertyEntry("Jugendarbeit", false, 0, true);
			TreePropertyEntry jugend = new TreePropertyEntry("Jugend", true, 1, true);
			TreePropertyEntry bewerbsvorbereitung = new TreePropertyEntry("Bewerbsvorbereitung FjWtLA", true, 1, true);
			TreePropertyEntry wissenstest = new TreePropertyEntry("Wissenstest", true, 1, true);
			TreePropertyRelationship relationshipJugend1 = new TreePropertyRelationship(jugendarbeit, jugend);
			TreePropertyRelationship relationshipJugend2 = new TreePropertyRelationship(jugendarbeit,
					bewerbsvorbereitung);
			TreePropertyRelationship relationshipJugend3 = new TreePropertyRelationship(jugendarbeit, wissenstest);
			this.setEntries(Stream
					.concat(this.getEntries().stream(),
							Arrays.asList(jugendarbeit, jugend, bewerbsvorbereitung, wissenstest).stream())
					.collect(Collectors.toList()));
			this.setRelationships(Stream
					.concat(this.getRelationships().stream(),
							Arrays.asList(relationshipJugend1, relationshipJugend2, relationshipJugend3).stream())
					.collect(Collectors.toList()));

			// Tätigkeit
			TreePropertyEntry taetigkeit = new TreePropertyEntry("Tätigkeit", false, 0, true);
			TreePropertyEntry arbeitenInDerFw = new TreePropertyEntry("Arbeiten in der FW", false, 1, true);
			TreePropertyEntry feuerwehrhausbau = new TreePropertyEntry("Feuerwehrhausbau", true, 2, true);
			TreePropertyEntry arbeitstag = new TreePropertyEntry("Arbeitstag", true, 2, true);
			TreePropertyRelationship relationshipTaetigkeit1 = new TreePropertyRelationship(taetigkeit,
					arbeitenInDerFw);
			TreePropertyRelationship relationshipTaetigkeit2 = new TreePropertyRelationship(arbeitenInDerFw,
					feuerwehrhausbau);
			TreePropertyRelationship relationshipTaetigkeit3 = new TreePropertyRelationship(arbeitenInDerFw,
					arbeitstag);
			this.setEntries(Stream
					.concat(this.getEntries().stream(),
							Arrays.asList(taetigkeit, arbeitenInDerFw, feuerwehrhausbau, arbeitstag).stream())
					.collect(Collectors.toList()));
			this.setRelationships(Stream
					.concat(this.getRelationships().stream(), Arrays
							.asList(relationshipTaetigkeit1, relationshipTaetigkeit2, relationshipTaetigkeit3).stream())
					.collect(Collectors.toList()));

			// Veranstaltung
			TreePropertyEntry veranstaltung = new TreePropertyEntry("Veranstaltung", false, 0, true);

			TreePropertyEntry organisation = new TreePropertyEntry("Organisation | Verwaltung", false, 1, true);
			TreePropertyEntry sonstigeV = new TreePropertyEntry("Sonstige Veranstaltung", false, 1, true);
			TreePropertyEntry atemschutzLt = new TreePropertyEntry("Atemschutz-Leistungstest", false, 1, true);
			TreePropertyEntry dienstbespraechung = new TreePropertyEntry("Dienstbesprechung (Sitzung)", false, 1, true);
			TreePropertyEntry ausflug = new TreePropertyEntry("Ausflug", false, 1, true);
			TreePropertyEntry ferienpass = new TreePropertyEntry("Ferienpass", false, 1, true);
			TreePropertyEntry fuehrung = new TreePropertyEntry("Führung", false, 1, true);
			TreePropertyEntry sveNachbetreuung = new TreePropertyEntry("SVE Nachbetreuung", false, 1, true);
			TreePropertyEntry sport = new TreePropertyEntry("Sport", false, 1, true);

			TreePropertyEntry dienstbespraechung2 = new TreePropertyEntry("Dienstbesprechung (Sitzung)", true, 2, true);
			TreePropertyEntry ausflug2 = new TreePropertyEntry("Ausflug", true, 2, true);
			TreePropertyEntry kirchenausruekung = new TreePropertyEntry("Kirchenausrückung", true, 2, true);
			TreePropertyEntry feuerwehrfest = new TreePropertyEntry("Feuerwehrfest / -ball", true, 2, true);
			TreePropertyEntry ArbeitstagV = new TreePropertyEntry("Arbeitstag", true, 2, true);
			TreePropertyEntry wahlveranstaltung = new TreePropertyEntry("Wahlveranstaltung", true, 2, true);
			TreePropertyEntry sonstigeV2 = new TreePropertyEntry("Sonstige Veranstaltung", true, 2, true);
			TreePropertyEntry vollversammlung = new TreePropertyEntry("Vollversammlung", true, 2, true);
			TreePropertyEntry inspektion = new TreePropertyEntry("Inspektion", true, 2, true);
			TreePropertyEntry bezirkstag = new TreePropertyEntry("Bezirkstagung", true, 2, true);
			TreePropertyEntry mitgliederversammlung = new TreePropertyEntry("Mitgliederversammlung", true, 2, true);
			TreePropertyEntry versammlung = new TreePropertyEntry("Versammlungen", true, 2, true);
			TreePropertyEntry sonstigeV3 = new TreePropertyEntry("sonstige Veranstaltungen", true, 2, true);
			TreePropertyEntry ausrueckung = new TreePropertyEntry("Ausrückung", true, 2, true);

			TreePropertyEntry sonstiges2 = new TreePropertyEntry("Sonstiges", true, 2, true);
			TreePropertyEntry sonstiges3 = new TreePropertyEntry("Sonstiges", true, 2, true);
			TreePropertyEntry sonstiges4 = new TreePropertyEntry("Sonstiges", true, 2, true);
			TreePropertyEntry sonstiges5 = new TreePropertyEntry("Sonstiges", true, 2, true);
			TreePropertyEntry sonstiges6 = new TreePropertyEntry("Sonstiges", true, 2, true);
			TreePropertyEntry sonstiges7 = new TreePropertyEntry("Sonstiges", true, 2, true);
			TreePropertyEntry sonstiges8 = new TreePropertyEntry("Sonstiges", true, 2, true);
			TreePropertyEntry sonstiges9 = new TreePropertyEntry("Sonstiges", true, 2, true);

			TreePropertyRelationship rsVer1 = new TreePropertyRelationship(veranstaltung, organisation, true);
			TreePropertyRelationship rsVer2 = new TreePropertyRelationship(veranstaltung, sonstigeV, true);
			TreePropertyRelationship rsVer3 = new TreePropertyRelationship(veranstaltung, atemschutzLt, true);
			TreePropertyRelationship rsVer4 = new TreePropertyRelationship(veranstaltung, dienstbespraechung, true);
			TreePropertyRelationship rsVer5 = new TreePropertyRelationship(veranstaltung, ausflug, true);
			TreePropertyRelationship rsVer6 = new TreePropertyRelationship(veranstaltung, ferienpass, true);
			TreePropertyRelationship rsVer7 = new TreePropertyRelationship(veranstaltung, fuehrung, true);
			TreePropertyRelationship rsVer8 = new TreePropertyRelationship(veranstaltung, sveNachbetreuung, true);
			TreePropertyRelationship rsVer9 = new TreePropertyRelationship(veranstaltung, sport, true);
			TreePropertyRelationship rsVer10 = new TreePropertyRelationship(organisation, dienstbespraechung2, true);
			TreePropertyRelationship rsVer11 = new TreePropertyRelationship(organisation, ausflug2, true);
			TreePropertyRelationship rsVer12 = new TreePropertyRelationship(organisation, kirchenausruekung, true);
			TreePropertyRelationship rsVer13 = new TreePropertyRelationship(organisation, feuerwehrfest, true);
			TreePropertyRelationship rsVer14 = new TreePropertyRelationship(organisation, ArbeitstagV, true);
			TreePropertyRelationship rsVer15 = new TreePropertyRelationship(organisation, wahlveranstaltung, true);
			TreePropertyRelationship rsVer16 = new TreePropertyRelationship(organisation, sonstigeV2, true);
			TreePropertyRelationship rsVer17 = new TreePropertyRelationship(organisation, vollversammlung, true);
			TreePropertyRelationship rsVer18 = new TreePropertyRelationship(organisation, inspektion, true);
			TreePropertyRelationship rsVer19 = new TreePropertyRelationship(organisation, bezirkstag, true);
			TreePropertyRelationship rsVer20 = new TreePropertyRelationship(organisation, mitgliederversammlung, true);
			TreePropertyRelationship rsVer21 = new TreePropertyRelationship(organisation, versammlung, true);
			TreePropertyRelationship rsVer22 = new TreePropertyRelationship(organisation, sonstigeV3, true);
			TreePropertyRelationship rsVer23 = new TreePropertyRelationship(organisation, ausrueckung, true);
			TreePropertyRelationship rsVer24 = new TreePropertyRelationship(sonstigeV, sonstiges2, true);
			TreePropertyRelationship rsVer25 = new TreePropertyRelationship(atemschutzLt, sonstiges3, true);
			TreePropertyRelationship rsVer26 = new TreePropertyRelationship(dienstbespraechung, sonstiges4, true);
			TreePropertyRelationship rsVer27 = new TreePropertyRelationship(ausflug, sonstiges5, true);
			TreePropertyRelationship rsVer28 = new TreePropertyRelationship(ferienpass, sonstiges6, true);
			TreePropertyRelationship rsVer29 = new TreePropertyRelationship(fuehrung, sonstiges7, true);
			TreePropertyRelationship rsVer30 = new TreePropertyRelationship(sveNachbetreuung, sonstiges8, true);
			TreePropertyRelationship rsVer31 = new TreePropertyRelationship(sport, sonstiges9, true);

			this.setEntries(Stream
					.concat(this.getEntries().stream(),
							Arrays.asList(veranstaltung, organisation, sonstigeV, atemschutzLt, dienstbespraechung,
									ausflug, ferienpass, fuehrung, sveNachbetreuung, sport, dienstbespraechung2,
									ausflug2, kirchenausruekung, feuerwehrfest, ArbeitstagV, wahlveranstaltung,
									sonstigeV2, vollversammlung, inspektion, bezirkstag, mitgliederversammlung,
									versammlung, sonstigeV3, ausrueckung, sonstiges2, sonstiges3, sonstiges4,
									sonstiges5, sonstiges6, sonstiges7, sonstiges8, sonstiges9).stream())
					.collect(Collectors.toList()));
			this.setRelationships(Stream.concat(this.getRelationships().stream(), Arrays
					.asList(rsVer1, rsVer2, rsVer3, rsVer4, rsVer5, rsVer6, rsVer7, rsVer8, rsVer9, rsVer10, rsVer11,
							rsVer12, rsVer13, rsVer14, rsVer15, rsVer16, rsVer17, rsVer18, rsVer19, rsVer20, rsVer21,
							rsVer22, rsVer23, rsVer24, rsVer25, rsVer26, rsVer27, rsVer28, rsVer29, rsVer30, rsVer31)
					.stream()).collect(Collectors.toList()));

			TreePropertyRelationship rel1 = new TreePropertyRelationship(this.getId(), einsatz.getId(), true);
			TreePropertyRelationship rel2 = new TreePropertyRelationship(this.getId(), bewerb.getId(), true);
			TreePropertyRelationship rel3 = new TreePropertyRelationship(this.getId(), jugendarbeit.getId(), true);
			TreePropertyRelationship rel4 = new TreePropertyRelationship(this.getId(), taetigkeit.getId(), true);
			TreePropertyRelationship rel5 = new TreePropertyRelationship(this.getId(), veranstaltung.getId(), true);
			TreePropertyRelationship rel6 = new TreePropertyRelationship(this.getId(), ausbildung.getId(), true);

			this.setRelationships(Stream.concat(this.getRelationships().stream(),
					Arrays.asList(rel1, rel2, rel3, rel4, rel5, rel6).stream()).collect(Collectors.toList()));
		}
	}

	public static class RankProperty extends TextPropertyDefinition {

		RankProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("Rank");
			this.setTenantId(tenantId);
		}
	}

	public static class DurationProperty extends LongPropertyDefinition {

		DurationProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("Duration");
			this.setRequired(true);
			this.setTenantId(tenantId);
		}
	}

	/**
	 * 
	 * TestProperties
	 * 
	 */

	public static class TestTextProperty extends TextPropertyDefinition {

		TestTextProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("Test TextProperty");
			this.setTenantId(tenantId);
		}
	}

	public static class TestLongTextProperty extends LongTextPropertyDefinition {

		TestLongTextProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("Test LongTextProperty");
			this.setTenantId(tenantId);
		}
	}

	public static class TestWholeNumberProperty extends LongPropertyDefinition {

		TestWholeNumberProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("Test WholeNumberProperty");
			this.setTenantId(tenantId);
		}
	}

	public static class TestFloatNumberProperty extends DoublePropertyDefinition {

		TestFloatNumberProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("Test FloatNumberProperty");
			this.setTenantId(tenantId);
		}
	}

	public static class TestBooleanProperty extends BooleanPropertyDefinition {

		TestBooleanProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("Test BooleanProperty");
			this.setTenantId(tenantId);
		}
	}

	public static class TestDateProperty extends DatePropertyDefinition {

		TestDateProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("Test DateProperty");
			this.setTenantId(tenantId);
		}
	}

	/**
	 * 
	 * Standard Properties
	 *
	 */

	// =========================================
	// ========== Text Properties ==============
	// =========================================
	public static class NameProperty extends TextPropertyDefinition {

		NameProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setType(PropertyType.TEXT);
			this.setName("Name");
			this.setRequired(false);
			this.setTenantId(tenantId);

			// List<PropertyConstraint<?>> constraints = new ArrayList<>();
			// constraints.add(new MinimumTextLength(3));
			// constraints.add(new MaximumTextLength(10));
			// constraints.add(new TextPattern("^[A-Za-z][A-Za-zöäüÖÄÜß\\s]*"));
			// this.setPropertyConstraints(new ArrayList(constraints));

		}
	}

	public static class DescriptionProperty extends LongTextPropertyDefinition {

		public DescriptionProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("Description");
			this.setRequired(true);
			this.setTenantId(tenantId);
		}
	}

	public static class WorkflowKeyProperty extends TextPropertyDefinition {
		public WorkflowKeyProperty(String tenantId) {
			inst(tenantId);
		}

		@PostConstruct
		public void inst(String tenantId) {
			this.setName("Workflow Key");
			this.setTenantId(tenantId);
		}
	}

	public static class ContentProperty extends TextPropertyDefinition {
		public ContentProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Content");
			this.setTenantId(tenantId);
		}
	}

	public static class PriorityProperty extends TextPropertyDefinition {
		public PriorityProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Priority");
			this.setTenantId(tenantId);

			List<String> legalValues = new ArrayList<>();
			legalValues.add("Low");
			legalValues.add("Normal");
			legalValues.add("High");
			legalValues.add("Critical");
			this.setAllowedValues(legalValues);
		}
	}

	public static class ImportancyProperty extends TextPropertyDefinition {
		public ImportancyProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Importancy");
			this.setTenantId(tenantId);

			List<String> legalValues = new ArrayList<>();
			legalValues.add("Not Important");
			legalValues.add("Somewhat Important");
			legalValues.add("Important");
			legalValues.add("Very Important");
			legalValues.add("Critically Important");
			this.setAllowedValues(legalValues);

		}
	}

	public static class RoleProperty extends TextPropertyDefinition {
		public RoleProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Role");
			this.setTenantId(tenantId);
		}
	}

	// public static class LocationProperty extends TextPropertyDefinition {
	// public LocationProperty(String tenantId) {
	// inst(tenantId);
	// }
	//
	// public void inst(String tenantId) {
	// this.setName("Location");
	// this.setTenantId(tenantId);
	// }
	// }

	public static class RequiredEquipmentProperty extends TextPropertyDefinition {
		public RequiredEquipmentProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Required Equipment");
			this.setTenantId(tenantId);
		}
	}

	public static class WorkshiftProperty extends TextPropertyDefinition {
		public WorkshiftProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Allocated Shift");
			this.setTenantId(tenantId);

			List<String> legalValues = new ArrayList<>();
			legalValues.add("Morning");
			legalValues.add("Day");
			legalValues.add("Evening");
			legalValues.add("Evening-Night");
			legalValues.add("Night");
			legalValues.add("Night-Morning");
			this.setAllowedValues(legalValues);
		}
	}

	public static class TaskPeriodTypeProperty extends TextPropertyDefinition {
		public TaskPeriodTypeProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Period Type");
			this.setTenantId(tenantId);

			List<String> legalValues = new ArrayList<>();
			legalValues.add("Days");
			legalValues.add("Weeks");
			legalValues.add("Months");
			legalValues.add("Weekly");
			legalValues.add("Daily");
			legalValues.add("Monthly");
			this.setAllowedValues(legalValues);

		}
	}

	public static class KeywordsProperty extends TextPropertyDefinition {
		public KeywordsProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Keywords");
			this.setTenantId(tenantId);
		}

	}

	//// Rewards ??
	public static class RewardsProperty extends TextPropertyDefinition {
		public RewardsProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Offered Reward(s)");
			this.setTenantId(tenantId);
		}
	}

	// =========================================
	// ========== Number Properties ============
	// =========================================

	public static class PostcodeProperty extends LongPropertyDefinition {
		public PostcodeProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Postcode");
			this.setTenantId(tenantId);

		}
	}

	public static class NumberOfVolunteersProperty extends LongPropertyDefinition {
		public NumberOfVolunteersProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Number of Volunteers");
			this.setTenantId(tenantId);

			List<Long> legalValues = new LinkedList<>();
			for (long i = 1; i <= 10; i++) {
				legalValues.add(i);
			}
			this.setAllowedValues(legalValues);
		}
	}

	public static class TaskPeriodValueProperty extends LongPropertyDefinition {
		public TaskPeriodValueProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Period length");
			this.setTenantId(tenantId);

			List<Long> defaultValues = new ArrayList<>();
			defaultValues.add(1L);
			this.setAllowedValues(defaultValues);
		}
	}

	public static class VolunteerAgeProperty extends LongPropertyDefinition {
		public VolunteerAgeProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Alter");
			this.setTenantId(tenantId);
		}
	}

	// =========================================
	// ========== Date Properties ==============
	// =========================================

	public static class StartDateProperty extends DatePropertyDefinition {
		public StartDateProperty(String tenantId) {
			super();
			this.setName("Starting Date");
			this.setTenantId(tenantId);
			setTestValues();
		}

		public void setTestValues() {

			List<Date> defaultValues = new ArrayList<>();
			defaultValues.add(new Date());
			this.setAllowedValues(defaultValues);

		}
	}

	public static class EndDateProperty extends DatePropertyDefinition {
		public EndDateProperty(String tenantId) {
			this.setName("End Date");
			this.setTenantId(tenantId);
			setTestValues();
		}

		public void setTestValues() {

		}
	}

	// =========================================
	// ========== Bool Properties ==============
	// =========================================

	public static class UrgentProperty extends BooleanPropertyDefinition {
		public UrgentProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Urgent");
			this.setTenantId(tenantId);
		}
	}

	public static class HighlightedProperty extends BooleanPropertyDefinition {
		public HighlightedProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Highlighted");
			this.setTenantId(tenantId);
		}
	}

	public static class PromotedProperty extends BooleanPropertyDefinition {
		public PromotedProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Promotion");
			this.setTenantId(tenantId);
		}
	}

	public static class FeedbackRequestedProperty extends BooleanPropertyDefinition {
		public FeedbackRequestedProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Feedback Requested");
			this.setTenantId(tenantId);
		}
	}

	public static class RemindParticipantsProperty extends BooleanPropertyDefinition {
		public RemindParticipantsProperty(String tenantId) {
			inst(tenantId);
		}

		public void inst(String tenantId) {
			this.setName("Remind Participants");
			this.setTenantId(tenantId);
		}
	}

	// =========================================
	// ==== Floating Point Number Properties ===
	// =========================================

	public static class LatitudeProperty extends DoublePropertyDefinition {
		public LatitudeProperty(String tenantId) {
			inst(tenantId);
			setTestValues();
		}

		public void inst(String tenantId) {
			this.setName("Latitude");
			this.setTenantId(tenantId);
		}

		private void setTestValues() {

		}
	}

	public static class LongitudeProperty extends DoublePropertyDefinition {
		public LongitudeProperty(String tenantId) {
			inst(tenantId);
			setTestValues();
		}

		public void inst(String tenantId) {
			this.setName("Longitude");
			this.setTenantId(tenantId);
		}

		private void setTestValues() {

		}
	}

	// -----------------------------------------
	// --------------FlexProd Properties
	// -----------------------------------------

	public static class VerfuegbaresSchutzgasProperty extends TextPropertyDefinition {
		public VerfuegbaresSchutzgasProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("verfuegbaresschutzgas");
			this.setName("Verfügbares Schutzgas");
			this.setAllowedValues(new LinkedList<String>());
			this.getAllowedValues().add("H2");
			this.getAllowedValues().add("N2");
			this.getAllowedValues().add("75% N2");
		}
	}

	public static class BauartProperty extends TextPropertyDefinition {
		public BauartProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("bauart");
			this.setName("Bauart");
			this.setAllowedValues(new LinkedList<String>());
			this.getAllowedValues().add("Band");
			this.getAllowedValues().add("Draht");
		}
	}

	public static class KaltgewalztesMaterialZulaessigProperty extends BooleanPropertyDefinition {
		public KaltgewalztesMaterialZulaessigProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("kaltgewalztesmaterialzulaessig");
			this.setName("Kaltgewalztes Material zulässig");
		}
	}

	public static class WarmgewalztesMaterialZulaessigProperty extends BooleanPropertyDefinition {
		public WarmgewalztesMaterialZulaessigProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("warmgewalztesmaterialzulaessig");
			this.setName("Warmgewalztes Material zulässig");
		}
	}

	public static class BundEntfettenProperty extends BooleanPropertyDefinition {
		public BundEntfettenProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("bundentfetten");
			this.setName("Bund Entfetten");
		}
	}

	public static class ChargierhilfeProperty extends TextPropertyDefinition {
		public ChargierhilfeProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("chargierhilfe");
			this.setName("Chargierhilfe");
			this.setAllowedValues(new LinkedList<String>());
			this.getAllowedValues().add("Konvektoren");
			this.getAllowedValues().add("Tragerahmen");
			this.getAllowedValues().add("Zwischenrahmen");
			this.getAllowedValues().add("Kronenstöcke");
			this.getAllowedValues().add("Chargierkörbe");
		}
	}

	public static class InnendurchmesserProperty extends LongPropertyDefinition {
		public InnendurchmesserProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("innendurchmesser");
			this.setName("Innendurchmesser");
		}
	}

	public static class AussendurchmesserProperty extends LongPropertyDefinition {
		public AussendurchmesserProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("aussendurchmesser");
			this.setName("Außendurchmesser");
		}
	}

	public static class GluehzeitProperty extends LongPropertyDefinition {
		public GluehzeitProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("gluehzeit");
			this.setName("Glühzeit");
		}
	}

	public static class DurchsatzProperty extends LongPropertyDefinition {
		public DurchsatzProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("durchsatz");
			this.setName("Durchsatz");
		}
	}

	public static class MoeglicheInnendurchmesserProperty extends LongPropertyDefinition {
		public MoeglicheInnendurchmesserProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("moeglicheinnendurchmesser");
			this.setName("Mögliche Innendurchmesser");
			this.setMultiple(true);
		}
	}

	public static class MaxAussendurchmesserProperty extends LongPropertyDefinition {
		public MaxAussendurchmesserProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("maxaussendurchmesser");
			this.setName("Max. Außendurchmesser");
		}
	}

	public static class MaxChargierhoeheProperty extends LongPropertyDefinition {
		public MaxChargierhoeheProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("maxchargierhoehe");
			this.setName("Max. Chargierhöhe");
		}
	}

	public static class CQI9Property extends BooleanPropertyDefinition {
		public CQI9Property(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("cqi9");
			this.setName("CQI-9");
		}
	}

	public static class TUSProperty extends BooleanPropertyDefinition {
		public TUSProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("tus");
			this.setName("TUS");
		}
	}

	public static class LetzteWartungProperty extends DatePropertyDefinition {
		public LetzteWartungProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("letztewartung");
			this.setName("Letzte Wartung");
		}
	}

	public static class WartungsintervallProperty extends DatePropertyDefinition {
		public WartungsintervallProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("wartungsintervall");
			this.setName("Wartungsintervall");
		}
	}

	public static class BandstaerkeProperty extends BooleanPropertyDefinition {
		public BandstaerkeProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("bandstaerke");
			this.setName("Bandstärke");
		}
	}

	public static class WarmgewalztProperty extends BooleanPropertyDefinition {
		public WarmgewalztProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("warmgewalzt");
			this.setName("Warmgewalzt");
		}
	}

	public static class KaltgewalztProperty extends BooleanPropertyDefinition {
		public KaltgewalztProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("kaltgewalzt");
			this.setName("Kaltgewalzt");
		}
	}

	public static class WalzartProperty extends TextPropertyDefinition {
		public WalzartProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("walzart");
			this.setName("Walzart");
			this.setAllowedValues(new LinkedList<String>());
			this.getAllowedValues().add("Warmgewalzt");
			this.getAllowedValues().add("Kaltgewalzt");
		}
	}

	public static class StreckgrenzeProperty extends LongPropertyDefinition {
		public StreckgrenzeProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("streckgrenze");
			this.setName("Streckgrenze");
		}
	}

	public static class DehnungProperty extends LongPropertyDefinition {
		public DehnungProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("dehnung");
			this.setName("Dehnung");
		}
	}

	public static class GefuegeProperty extends TextPropertyDefinition {
		public GefuegeProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("gefuege");
			this.setName("Gefüge");
		}
	}

	public static class MaterialBereitgestelltProperty extends BooleanPropertyDefinition {
		public MaterialBereitgestelltProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("materialbereitgestellt");
			this.setName("Material bereitgestellt?");
		}
	}

	public static class VerpackungProperty extends TextPropertyDefinition {
		public VerpackungProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("verpackung");
			this.setName("Verpackung");
		}
	}

	public static class TransportartProperty extends TextPropertyDefinition {
		public TransportartProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("transportart");
			this.setName("Transportart");
			this.setAllowedValues(new LinkedList<String>());
			this.getAllowedValues().add("LKW");
			this.getAllowedValues().add("Zug");
			this.getAllowedValues().add("Schiff");
			this.getAllowedValues().add("Sonstiges");
		}
	}

	public static class ZahlungsbedingungenProperty extends LongTextPropertyDefinition {
		public ZahlungsbedingungenProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("zahlungsbedingungen");
			this.setName("Zahlungsbedingungen");
		}
	}

	public static class TitelProperty extends TextPropertyDefinition {
		TitelProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("titel");
			this.setName("Titel");
		}
	}

	public static class ProdukttypProperty extends TextPropertyDefinition {
		ProdukttypProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("produkttyp");
			this.setName("Produkttyp");

			this.setAllowedValues(new ArrayList<String>());
			this.getAllowedValues().add("Band");
			this.getAllowedValues().add("Draht");
			this.getAllowedValues().add("Band & Draht");
		}
	}

	public static class MengeProperty extends DoublePropertyDefinition {
		MengeProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("menge");
			this.setName("Menge");
			this.setUnit("t");
		}
	}

	public static class MinimaleMengeProperty extends DoublePropertyDefinition {
		MinimaleMengeProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("minimale_menge");
			this.setName("minimale Menge");
			this.setUnit("t");
		}
	}

	public static class LieferdatumProperty extends DatePropertyDefinition {
		LieferdatumProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("lieferdatum");
			this.setName("Lieferdatum (spätestens)");
		}
	}

	public static class WerkstoffBereitgestelltProperty extends TextPropertyDefinition {
		WerkstoffBereitgestelltProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("werkstoff_bereitgestellt");
			this.setName("Werkstoff bereitgestellt");
			this.setAllowedValues(new ArrayList<>());
			this.getAllowedValues().add("Ja");
			this.getAllowedValues().add("Nein");
		}
	}

	public static class BeschreibungZusatzinfoProperty extends LongTextPropertyDefinition {
		BeschreibungZusatzinfoProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("allgemeine_beschreibung");
			this.setName("allgemeine Beschreibung / Zusatzinformationen");
		}
	}

	public static class DurchmesserInnenProperty extends LongPropertyDefinition {
		public DurchmesserInnenProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("durchmesser_innen");
			this.setName("Durchmesser (innen)");
			this.setUnit("mm");
		}
	}

	public static class DurchmesserAussenProperty extends LongPropertyDefinition {
		public DurchmesserAussenProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("durchmesser_aussen");
			this.setName("Durchmesser (außen)");
			this.setUnit("mm");
		}
	}

	public static class HoeheProperty extends LongPropertyDefinition {
		public HoeheProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("hoehe");
			this.setName("Höhe");
			this.setUnit("mm");
		}
	}

	public static class WerkstoffProperty extends TextPropertyDefinition {
		public WerkstoffProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("werkstoff");
			this.setName("Werkstoff");
			this.setAllowedValues(new ArrayList<>());
			this.getAllowedValues().add("Eintrag 1");
			this.getAllowedValues().add("Eintrag 2");
			this.getAllowedValues().add("Eintrag 3");
			this.getAllowedValues().add("...");
			this.getAllowedValues().add("Freitext");
		}
	}

	public static class DurchmesserProperty extends LongPropertyDefinition {
		public DurchmesserProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("durchmesser");
			this.setName("Durchmesser");
			this.setUnit("mm");
		}
	}

	public static class WerkstoffFreitextProperty extends LongTextPropertyDefinition {
		public WerkstoffFreitextProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("werkstoff_freitext");
			this.setName("Werkstoff (Freitext)");
		}
	}

	public static class ZugfestigkeitProperty extends LongPropertyDefinition {
		public ZugfestigkeitProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("zugfestigkeit");
			this.setName("Zugfestigkeit");
			this.setUnit("N/mm²");
		}
	}

	public static class SchutzgasProperty extends TextPropertyDefinition {
		public SchutzgasProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("schutzgas");
			this.setName("Schutzgas");
			this.setAllowedValues(new ArrayList<>());
			this.getAllowedValues().add("H2 0%, N2 100%");
			this.getAllowedValues().add("H2 10%, N2 90%");
			this.getAllowedValues().add("H2 20%, N2 80%");
			this.getAllowedValues().add("H2 30%, N2 70%");
			this.getAllowedValues().add("H2 40%, N2 60%");
			this.getAllowedValues().add("H2 50%, N2 50%");
			this.getAllowedValues().add("H2 60%, N2 40%");
			this.getAllowedValues().add("H2 70%, N2 30%");
			this.getAllowedValues().add("H2 80%, N2 20%");
			this.getAllowedValues().add("H2 90%, N2 10%");
			this.getAllowedValues().add("H2 100%, N2 0%");
		}
	}

	public static class GluehreiseProperty extends TextPropertyDefinition {
		public GluehreiseProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("gluehreise");
			this.setName("Glühprogramm / -reise");
		}
	}

	public static class ErforderlicheTemperaturhomogenitaetProperty extends LongPropertyDefinition {
		public ErforderlicheTemperaturhomogenitaetProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("erforderliche_temperaturhomogenitaet");
			this.setName("erforderliche Temperaturhomogenität");
			this.setUnit("°C (+/-)");
		}
	}

	public static class OberflaechenqualitaetProperty extends TextPropertyDefinition {
		public OberflaechenqualitaetProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("oberflaechenqualitaet");
			this.setName("Oberflächenqualität");
			this.setAllowedValues(new ArrayList<>());
			this.getAllowedValues().add("blank");
			this.getAllowedValues().add("schwarz");
		}
	}

	public static class ZusaetzlicheProduktinformationenProperty extends LongTextPropertyDefinition {
		public ZusaetzlicheProduktinformationenProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("zusaetzliche_produktinformationen");
			this.setName("Zusätzliche Produkt- und Bearbeitungsinformationen");
		}
	}

	public static class IncotermsProperty extends TextPropertyDefinition {
		public IncotermsProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("incoterms");
			this.setName("Incoterms");
			this.setAllowedValues(new ArrayList<>());
			this.getAllowedValues().add("EXW");
			this.getAllowedValues().add("DAP");
		}
	}

	public static class LieferortProperty extends TextPropertyDefinition {
		public LieferortProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("lieferort");
			this.setName("Lieferort");
		}
	}

	public static class AbholortProperty extends TextPropertyDefinition {
		public AbholortProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("abholort");
			this.setName("Abholort");
		}
	}

	public static class VerpackungsvorgabenProperty extends LongTextPropertyDefinition {
		public VerpackungsvorgabenProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("verpackungsvorgaben");
			this.setName("Verpackungsvorgaben");
		}
	}

	public static class BandDickeProperty extends LongPropertyDefinition {
		public BandDickeProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("banddicke");
			this.setName("Banddicke");
			this.setUnit("mm");
		}
	}

	public static class BandBreiteProperty extends LongPropertyDefinition {
		public BandBreiteProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("bandbreite");
			this.setName("Bandbreite");
			this.setUnit("mm");
			;
		}
	}

	public static class DurchmesserKronenstockProperty extends LongPropertyDefinition {
		public DurchmesserKronenstockProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("durchmesser_kronenstock");
			this.setName("Durchmesser Kronenstock");
			this.setUnit("mm");
			;
		}
	}

	public static class MaximaldurchmesserBundProperty extends LongPropertyDefinition {
		public MaximaldurchmesserBundProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("maximaldurchmesser_bund");
			this.setName("Maximaldurchmesser Bund");
			this.setUnit("mm");
			;
		}
	}

	public static class DurchmesserDornProperty extends LongPropertyDefinition {
		public DurchmesserDornProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("durchmesser_dorn");
			this.setName("Durchmesser Dorn");
			this.setUnit("mm");
			;
		}
	}

	public static class InnendurchmesserOfenProperty extends LongPropertyDefinition {
		public InnendurchmesserOfenProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("innendurchmesser_ofen");
			this.setName("Innendurchmesser Ofen");
			this.setUnit("mm");
			;
		}
	}

	public static class OfenHoeheProperty extends LongPropertyDefinition {
		public OfenHoeheProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("ofenhoehe");
			this.setName("Ofenhöhe");
			this.setUnit("mm");
			;
		}
	}

	public static class MaxGluehtemperaturProperty extends LongPropertyDefinition {
		public MaxGluehtemperaturProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("max_gluehtemperatur");
			this.setName("Max. Glühtemperatur");
			this.setUnit("°C");
			;
		}
	}

	public static class TemperaturhomogenitaetProperty extends LongPropertyDefinition {
		public TemperaturhomogenitaetProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("temperaturhomogenitaet");
			this.setName("Temperaturhomogenität");
			this.setUnit("°C");
			;
		}
	}

	public static class AufheizrateProperty extends DoublePropertyDefinition {
		public AufheizrateProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("aufheizrate");
			this.setName("Aufheizrate");
		}
	}

	public static class AbkuehlrateProperty extends DoublePropertyDefinition {
		public AbkuehlrateProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("abkuehlrate");
			this.setName("Abkühlrate");
		}
	}

	public static class GluehprogrammVerfuegbarProperty extends BooleanPropertyDefinition {
		public GluehprogrammVerfuegbarProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("gluehprogramm_verfuegbar");
			this.setName("Glühprogramm /-reise verfügbar?");
		}
	}

	public static class MaxAnteilH2Property extends LongPropertyDefinition {
		public MaxAnteilH2Property(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("max_anteil_h2");
			this.setName("Maximaler Anteil H2");
			this.setAllowedValues(new ArrayList<>());
			this.getAllowedValues().add(10L);
			this.getAllowedValues().add(20L);
			this.getAllowedValues().add(30L);
			this.getAllowedValues().add(40L);
			this.getAllowedValues().add(50L);
			this.getAllowedValues().add(60L);
			this.getAllowedValues().add(70L);
			this.getAllowedValues().add(80L);
			this.getAllowedValues().add(90L);
			this.getAllowedValues().add(100L);
			this.setUnit("%");
		}
	}

	public static class KapazitaetProperty extends TextPropertyDefinition {
		public KapazitaetProperty(String tenantId) {
			this.setTenantId(tenantId);
			this.setId("kapazitaet");
			this.setName("Kapazität");
		}
	}

}
